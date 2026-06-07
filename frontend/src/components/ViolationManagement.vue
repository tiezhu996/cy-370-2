<script setup lang="ts">
import { ref, onMounted, computed } from "vue";
import { ElMessage } from "element-plus";
import { fetchViolationRecords, fetchViolationStats, markViolationHandled, type ViolationQueryParams } from "../api/client";
import type { ViolationRecord, ViolationUserStats, PageResponse } from "../types";

const loading = ref(false);
const statsLoading = ref(false);
const violationRecords = ref<ViolationRecord[]>([]);
const violationStats = ref<ViolationUserStats[]>([]);
const pageData = ref<PageResponse<ViolationRecord> | null>(null);

const startDate = ref("");
const endDate = ref("");
const currentPage = ref(1);
const pageSize = ref(10);
const sortBy = ref("violationTime");
const sortDirection = ref("DESC");

const activeTab = ref<"records" | "stats">("records");

const formatDateTime = (dateStr: string) => {
  if (!dateStr) return "-";
  const date = new Date(dateStr);
  return date.toLocaleString("zh-CN", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  });
};

const formatTime = (timeStr: string) => {
  if (!timeStr) return "-";
  return timeStr.substring(0, 5);
};

const formatDate = (dateStr: string) => {
  if (!dateStr) return "-";
  const date = new Date(dateStr);
  return date.toLocaleDateString("zh-CN", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  });
};

const getViolationTypeLabel = (type: string) => {
  const labels: Record<string, string> = {
    TIMEOUT_NO_CHECKIN: "超时未签到",
    EARLY_CHECKOUT: "提前签退",
    NO_SHOW: "未到馆",
  };
  return labels[type] || type;
};

const getViolationTypeTagType = (type: string) => {
  const types: Record<string, string> = {
    TIMEOUT_NO_CHECKIN: "danger",
    EARLY_CHECKOUT: "warning",
    NO_SHOW: "danger",
  };
  return types[type] || "info";
};

const buildQueryParams = (): ViolationQueryParams => {
  const params: ViolationQueryParams = {
    page: currentPage.value - 1,
    size: pageSize.value,
    sortBy: sortBy.value,
    sortDirection: sortDirection.value,
  };
  if (startDate.value) params.startDate = startDate.value;
  if (endDate.value) params.endDate = endDate.value;
  return params;
};

const loadRecords = async () => {
  loading.value = true;
  try {
    const data = await fetchViolationRecords(buildQueryParams());
    pageData.value = data;
    violationRecords.value = data.content;
  } catch (error) {
    ElMessage.error("加载违约记录失败");
    console.error(error);
  } finally {
    loading.value = false;
  }
};

const loadStats = async () => {
  statsLoading.value = true;
  try {
    const params: ViolationQueryParams = {};
    if (startDate.value) params.startDate = startDate.value;
    if (endDate.value) params.endDate = endDate.value;
    violationStats.value = await fetchViolationStats(params);
  } catch (error) {
    ElMessage.error("加载违约统计失败");
    console.error(error);
  } finally {
    statsLoading.value = false;
  }
};

const handleSearch = () => {
  currentPage.value = 1;
  loadRecords();
  loadStats();
};

const handleReset = () => {
  startDate.value = "";
  endDate.value = "";
  sortBy.value = "violationTime";
  sortDirection.value = "DESC";
  currentPage.value = 1;
  loadRecords();
  loadStats();
};

const handlePageChange = (page: number) => {
  currentPage.value = page;
  loadRecords();
};

const handleSizeChange = (size: number) => {
  pageSize.value = size;
  currentPage.value = 1;
  loadRecords();
};

const handleSortChange = ({ prop, order }: { prop: string; order: string | null }) => {
  if (prop && order) {
    sortBy.value = prop;
    sortDirection.value = order === "ascending" ? "ASC" : "DESC";
    loadRecords();
  }
};

const handleMarkHandled = async (record: ViolationRecord) => {
  try {
    const result = await markViolationHandled(record.id, !record.handled);
    if (result.success) {
      ElMessage.success(record.handled ? "已取消标记" : "已标记为已处理");
      loadRecords();
    } else {
      ElMessage.error(result.message);
    }
  } catch (error) {
    ElMessage.error("操作失败");
    console.error(error);
  }
};

const totalRecords = computed(() => pageData.value?.totalElements || 0);
const totalPages = computed(() => pageData.value?.totalPages || 0);

const violationCountStats = computed(() => {
  const total = violationStats.value.reduce((sum, s) => sum + s.violationCount, 0);
  const topOffender = violationStats.value[0];
  return { total, topOffender };
});

onMounted(() => {
  loadRecords();
  loadStats();
});
</script>

<template>
  <div class="violation-management">
    <div class="hero-panel">
      <span class="pill">违约记录管理</span>
      <h2>违约记录管理</h2>
      <p>管理员可按时间段筛选查看违约明细，支持按违约次数排序，方便重点跟进经常违约的用户。</p>
    </div>

    <div class="work-panel" style="margin-top: 26px;">
      <div class="search-form">
        <div class="search-row">
          <div class="search-item">
            <label>开始日期：</label>
            <el-date-picker
              v-model="startDate"
              type="date"
              placeholder="选择开始日期"
              value-format="YYYY-MM-DD"
              :clearable="true"
            />
          </div>
          <div class="search-item">
            <label>结束日期：</label>
            <el-date-picker
              v-model="endDate"
              type="date"
              placeholder="选择结束日期"
              value-format="YYYY-MM-DD"
              :clearable="true"
            />
          </div>
          <div class="search-item">
            <el-button type="primary" @click="handleSearch" :loading="loading">
              查询
            </el-button>
            <el-button @click="handleReset">重置</el-button>
          </div>
        </div>
      </div>

      <div class="stats-summary" v-if="!statsLoading && violationStats.length > 0">
        <div class="stat-card">
          <div class="stat-label">违约总次数</div>
          <div class="stat-value">{{ violationCountStats.total }}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">违约用户数</div>
          <div class="stat-value">{{ violationStats.length }}</div>
        </div>
        <div class="stat-card" v-if="violationCountStats.topOffender">
          <div class="stat-label">最高违约用户</div>
          <div class="stat-value">
            {{ violationCountStats.topOffender.userName }}
            <span class="stat-sub">({{ violationCountStats.topOffender.violationCount }}次)</span>
          </div>
        </div>
      </div>

      <el-tabs v-model="activeTab" style="margin-top: 24px;">
        <el-tab-pane label="违约明细" name="records">
          <el-table
            :data="violationRecords"
            style="width: 100%; margin-top: 16px;"
            v-loading="loading"
            @sort-change="handleSortChange"
            :default-sort="{ prop: 'violationTime', order: 'descending' }"
            size="large"
          >
            <el-table-column prop="userName" label="违约人" width="100" sortable="custom" />
            <el-table-column prop="seatCode" label="座位号" width="100" sortable="custom" />
            <el-table-column prop="reservationDate" label="预约日期" width="120" sortable="custom" :formatter="(row) => formatDate(row.reservationDate)" />
            <el-table-column label="预约时段" width="140">
              <template #default="{ row }">
                {{ formatTime(row.startTime) }} - {{ formatTime(row.endTime) }}
              </template>
            </el-table-column>
            <el-table-column prop="violationTime" label="违约时间" width="160" sortable="custom" :formatter="(row) => formatDateTime(row.violationTime)" />
            <el-table-column prop="violationType" label="违约类型" width="120">
              <template #default="{ row }">
                <el-tag :type="getViolationTypeTagType(row.violationType)">
                  {{ getViolationTypeLabel(row.violationType) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="description" label="违约说明" min-width="200" show-overflow-tooltip />
            <el-table-column prop="handled" label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="row.handled ? 'success' : 'warning'">
                  {{ row.handled ? '已处理' : '待处理' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120" fixed="right">
              <template #default="{ row }">
                <el-button
                  :type="row.handled ? 'info' : 'primary'"
                  size="small"
                  @click="handleMarkHandled(row)"
                >
                  {{ row.handled ? '取消标记' : '标记处理' }}
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination-wrapper">
            <el-pagination
              v-model:current-page="currentPage"
              v-model:page-size="pageSize"
              :page-sizes="[10, 20, 50, 100]"
              :total="totalRecords"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handlePageChange"
            />
          </div>
        </el-tab-pane>

        <el-tab-pane label="违约统计 (按次数排序)" name="stats">
          <el-table
            :data="violationStats"
            style="width: 100%; margin-top: 16px;"
            v-loading="statsLoading"
            size="large"
          >
            <el-table-column type="index" label="排名" width="80" align="center">
              <template #default="{ $index }">
                <el-tag v-if="$index < 3" :type="$index === 0 ? 'danger' : $index === 1 ? 'warning' : 'info'">
                  {{ $index + 1 }}
                </el-tag>
                <span v-else>{{ $index + 1 }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="userName" label="用户名称" min-width="150" />
            <el-table-column prop="userId" label="用户ID" width="100" />
            <el-table-column prop="violationCount" label="违约次数" width="120" sortable>
              <template #default="{ row }">
                <span :style="{ fontWeight: 'bold', color: row.violationCount >= 3 ? '#F56C6C' : row.violationCount >= 2 ? '#E6A23C' : '#67C23A' }">
                  {{ row.violationCount }} 次
                </span>
              </template>
            </el-table-column>
            <el-table-column label="风险等级" width="120">
              <template #default="{ row }">
                <el-tag v-if="row.violationCount >= 3" type="danger" effect="dark">高风险</el-tag>
                <el-tag v-else-if="row.violationCount >= 2" type="warning">中风险</el-tag>
                <el-tag v-else type="success">低风险</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120">
              <template #default="{ row }">
                <el-button type="primary" size="small" @click="() => { activeTab = 'records'; startDate = ''; endDate = ''; loadRecords(); }">
                  查看明细
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<style scoped>
.violation-management {
  width: min(1180px, calc(100vw - 32px));
  margin: 0 auto;
  padding: clamp(24px, 5vw, 56px) 0 64px;
}

.search-form {
  margin-bottom: 24px;
  padding: 20px;
  background: color-mix(in srgb, #257998 8%, transparent);
  border-radius: 8px;
}

.search-row {
  display: flex;
  gap: 20px;
  align-items: center;
  flex-wrap: wrap;
}

.search-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.search-item label {
  font-weight: 600;
  white-space: nowrap;
}

.stats-summary {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
  margin-top: 20px;
}

.stat-card {
  background: white;
  border-radius: 8px;
  padding: 20px;
  border: 1px solid color-mix(in srgb, #162329 10%, transparent);
}

.stat-label {
  font-size: 14px;
  color: color-mix(in srgb, #162329 60%, transparent);
  margin-bottom: 8px;
}

.stat-value {
  font-size: 28px;
  font-weight: 800;
  color: #257998;
}

.stat-sub {
  font-size: 14px;
  font-weight: 500;
  color: #d17a37;
  margin-left: 4px;
}

.pagination-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.hero-panel {
  border: 1px solid color-mix(in srgb, #162329 13%, transparent);
  background: color-mix(in srgb, #eef7fa 86%, white 14%);
  box-shadow: 0 18px 50px color-mix(in srgb, #162329 10%, transparent);
  border-radius: 8px;
  padding: clamp(22px, 4vw, 42px);
}

.hero-panel h2 {
  margin: 10px 0 6px;
  font-size: clamp(20px, 3vw, 28px);
}

.hero-panel p {
  color: color-mix(in srgb, #162329 72%, #257998 28%);
  font-size: clamp(15px, 2vw, 17px);
  line-height: 1.8;
  margin: 0;
}

.work-panel {
  border: 1px solid color-mix(in srgb, #162329 13%, transparent);
  background: color-mix(in srgb, #eef7fa 86%, white 14%);
  box-shadow: 0 18px 50px color-mix(in srgb, #162329 10%, transparent);
  border-radius: 8px;
  padding: clamp(22px, 4vw, 42px);
}

@media (max-width: 860px) {
  .stats-summary {
    grid-template-columns: 1fr;
  }

  .search-row {
    flex-direction: column;
    align-items: stretch;
  }

  .search-item {
    flex-direction: column;
    align-items: stretch;
  }
}
</style>

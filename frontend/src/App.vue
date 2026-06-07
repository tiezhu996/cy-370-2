<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { fetchOverview } from "./api/client";
import { APP_CODE, APP_NAME } from "./constants/app";
import { REQUEST_MESSAGES } from "./constants/messages";
import { createFallbackOverview } from "./state/dashboard";
import type { OverviewResponse } from "./types";
import { routes } from "./routes";
import FeatureStrip from "./components/FeatureStrip.vue";
import MetricGrid from "./components/MetricGrid.vue";
import OperationsTable from "./components/OperationsTable.vue";
import ViolationManagement from "./components/ViolationManagement.vue";

const currentPath = ref(window.location.hash.slice(1) || "/");
const overview = ref<OverviewResponse>(createFallbackOverview());
const notice = ref(REQUEST_MESSAGES.overviewFallback);

watch(currentPath, (newPath) => {
  window.location.hash = newPath;
});

window.addEventListener("hashchange", () => {
  currentPath.value = window.location.hash.slice(1) || "/";
});

function navigateTo(path: string) {
  currentPath.value = path;
}

function goHealth() {
  window.location.href = REQUEST_MESSAGES.healthPath;
}

onMounted(async () => {
  try {
    overview.value = await fetchOverview();
    notice.value = "后端服务已联通，当前展示实时接口数据。";
  } catch {
    notice.value = REQUEST_MESSAGES.overviewFallback;
  }
});
</script>

<template>
  <main class="app-shell">
    <header class="topbar">
      <div>
        <span class="brand-code">{{ APP_CODE }}</span>
        <h1 class="brand-title">{{ APP_NAME }}</h1>
      </div>
      <div class="nav-wrapper">
        <el-menu
          :default-active="currentPath"
          mode="horizontal"
          router
          @select="navigateTo"
          :ellipsis="false"
          class="nav-menu"
        >
          <el-menu-item v-for="route in routes" :key="route.path" :index="route.path">
            {{ route.label }}
          </el-menu-item>
        </el-menu>
        <el-button type="primary" @click="goHealth" size="small">API Health</el-button>
      </div>
    </header>

    <template v-if="currentPath === '/'">
      <section class="workspace">
        <div class="lead-grid">
          <article class="hero-panel">
            <span class="pill">{{ notice }}</span>
            <h2>{{ overview.appName }}</h2>
            <p>{{ overview.description }}</p>
          </article>
          <MetricGrid :items="overview.kpis" />
        </div>
        <FeatureStrip :items="overview.features" />
        <section class="work-panel">
          <h2>运营任务流</h2>
          <OperationsTable :records="overview.records" />
        </section>
      </section>
    </template>

    <template v-else-if="currentPath === '/violations'">
      <ViolationManagement />
    </template>

    <template v-else>
      <section class="workspace">
        <div class="hero-panel">
          <span class="pill">功能开发中</span>
          <h2>{{ routes.find(r => r.path === currentPath)?.label || '页面' }}</h2>
          <p>该功能正在开发中，敬请期待。</p>
        </div>
      </section>
    </template>
  </main>
</template>

<style scoped>
.nav-wrapper {
  display: flex;
  align-items: center;
  gap: 20px;
}

.nav-menu {
  background: transparent;
  border: none;
}

.nav-menu :deep(.el-menu-item) {
  color: color-mix(in srgb, #162329 70%, transparent);
  font-weight: 600;
  border-bottom: 2px solid transparent;
}

.nav-menu :deep(.el-menu-item:hover) {
  color: #257998;
  background: transparent;
}

.nav-menu :deep(.el-menu-item.is-active) {
  color: #257998;
  border-bottom-color: #257998;
  background: transparent;
}

@media (max-width: 860px) {
  .nav-wrapper {
    flex-direction: column;
    align-items: stretch;
    width: 100%;
  }

  .nav-menu {
    overflow-x: auto;
  }
}
</style>

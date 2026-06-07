import type { FeatureItem, KpiItem, OperationRecord } from "../types";

export const localFeatures: FeatureItem[] = [
  {
    "id": 1,
    "title": "楼层座位图可视化",
    "description": "以楼层平面图展示所有座位分布，区分普通座位、靠窗座位、带电源座位，用颜色标识空闲/已约/使用中状态。",
    "status": "已上线",
    "metric": "88%"
  },
  {
    "id": 2,
    "title": "按时段预约与选座",
    "description": "用户选择日期和时段（最小单位30分钟或1小时），在座位图上点选空闲座位，系统自动校验时段冲突，预约成功生成二维码。",
    "status": "排期中",
    "metric": "31 单"
  },
  {
    "id": 3,
    "title": "扫码签到与违规释放",
    "description": "预约开始前后15分钟内扫码签到，超时未签到自动释放座位并标记违约，使用结束需扫码签退，记录实际使用时长。",
    "status": "巡检中",
    "metric": "10 项"
  },
  {
    "id": 4,
    "title": "座位偏好记忆",
    "description": "系统自动记录用户历史预约偏好（如常选楼层、靠窗座位），下次预约时优先推荐相似座位，提升预约效率。",
    "status": "优化中",
    "metric": "4 级"
  },
  {
    "id": 5,
    "title": "学习小组研讨间预约",
    "description": "提供独立研讨间（4-8人）预约功能，发起人邀请组员加入，预约成功后所有组员收到通知，研讨间配备白板和投影设备记录。",
    "status": "可导出",
    "metric": "28 条"
  }
];

export const localKpis: KpiItem[] = [
  {
    "label": "今日处理",
    "value": "114",
    "trend": "+12%",
    "tone": "primary"
  },
  {
    "label": "预约/订单",
    "value": "55",
    "trend": "+8%",
    "tone": "warm"
  },
  {
    "label": "履约率",
    "value": "91%",
    "trend": "+3%",
    "tone": "cool"
  },
  {
    "label": "待处理",
    "value": "8",
    "trend": "需跟进",
    "tone": "neutral"
  }
];

export const operationRecords: OperationRecord[] = [
  {
    "key": "ldlibseat-1",
    "name": "楼层座位图可视化",
    "owner": "运营组",
    "status": "已上线",
    "metric": "88%",
    "priority": "高"
  },
  {
    "key": "ldlibseat-2",
    "name": "按时段预约与选座",
    "owner": "管理员",
    "status": "排期中",
    "metric": "31 单",
    "priority": "中"
  },
  {
    "key": "ldlibseat-3",
    "name": "扫码签到与违规释放",
    "owner": "服务台",
    "status": "巡检中",
    "metric": "10 项",
    "priority": "低"
  },
  {
    "key": "ldlibseat-4",
    "name": "座位偏好记忆",
    "owner": "财务组",
    "status": "优化中",
    "metric": "4 级",
    "priority": "高"
  },
  {
    "key": "ldlibseat-5",
    "name": "学习小组研讨间预约",
    "owner": "审核组",
    "status": "可导出",
    "metric": "28 条",
    "priority": "中"
  }
];

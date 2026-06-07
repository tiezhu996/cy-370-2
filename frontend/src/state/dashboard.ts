import { localFeatures, localKpis, operationRecords } from "../data/workbench";
import type { OverviewResponse } from "../types";
import { APP_CODE, APP_NAME } from "../constants/app";

export function createFallbackOverview(): OverviewResponse {
  return {
    appName: APP_NAME,
    appCode: APP_CODE,
    description: "面向高校图书馆，提供座位可视化预约、签到管理和学习小组研讨间预约的智能化系统。",
    features: localFeatures,
    kpis: localKpis,
    records: operationRecords,
  };
}

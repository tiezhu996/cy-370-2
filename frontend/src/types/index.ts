export interface FeatureItem {
  id: number;
  title: string;
  description: string;
  status: string;
  metric: string;
}

export interface KpiItem {
  label: string;
  value: string;
  trend: string;
  tone: string;
}

export interface OperationRecord {
  key: string;
  name: string;
  owner: string;
  status: string;
  metric: string;
  priority: string;
}

export interface OverviewResponse {
  appName: string;
  appCode: string;
  description: string;
  features: FeatureItem[];
  kpis: KpiItem[];
  records: OperationRecord[];
}

export interface ViolationRecord {
  id: number;
  userId: number;
  userName: string;
  reservationId: number;
  seatId: number;
  seatCode: string;
  violationType: string;
  violationTime: string;
  reservationDate: string;
  startTime: string;
  endTime: string;
  description: string;
  handled: boolean;
  createdAt: string;
}

export interface ViolationUserStats {
  userId: number;
  userName: string;
  violationCount: number;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

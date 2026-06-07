import { API_BASE_URL } from "../constants/app";
import type { OverviewResponse, ViolationRecord, ViolationUserStats, PageResponse } from "../types";

export async function fetchOverview(): Promise<OverviewResponse> {
  const response = await fetch(`${API_BASE_URL}/overview`, {
    headers: { Accept: "application/json" },
  });

  if (!response.ok) {
    throw new Error(`Overview request failed: ${response.status}`);
  }

  return response.json() as Promise<OverviewResponse>;
}

export interface ViolationQueryParams {
  startDate?: string;
  endDate?: string;
  page?: number;
  size?: number;
  sortBy?: string;
  sortDirection?: string;
}

export async function fetchViolationRecords(params: ViolationQueryParams = {}): Promise<PageResponse<ViolationRecord>> {
  const queryParams = new URLSearchParams();
  if (params.startDate) queryParams.set("startDate", params.startDate);
  if (params.endDate) queryParams.set("endDate", params.endDate);
  if (params.page !== undefined) queryParams.set("page", params.page.toString());
  if (params.size !== undefined) queryParams.set("size", params.size.toString());
  if (params.sortBy) queryParams.set("sortBy", params.sortBy);
  if (params.sortDirection) queryParams.set("sortDirection", params.sortDirection);

  const url = `${API_BASE_URL}/admin/violations${queryParams.toString() ? `?${queryParams.toString()}` : ""}`;
  const response = await fetch(url, {
    headers: { Accept: "application/json" },
  });

  if (!response.ok) {
    throw new Error(`Violation records request failed: ${response.status}`);
  }

  return response.json() as Promise<PageResponse<ViolationRecord>>;
}

export async function fetchViolationStats(params: ViolationQueryParams = {}): Promise<ViolationUserStats[]> {
  const queryParams = new URLSearchParams();
  if (params.startDate) queryParams.set("startDate", params.startDate);
  if (params.endDate) queryParams.set("endDate", params.endDate);

  const url = `${API_BASE_URL}/admin/violations/stats${queryParams.toString() ? `?${queryParams.toString()}` : ""}`;
  const response = await fetch(url, {
    headers: { Accept: "application/json" },
  });

  if (!response.ok) {
    throw new Error(`Violation stats request failed: ${response.status}`);
  }

  return response.json() as Promise<ViolationUserStats[]>;
}

export async function markViolationHandled(id: number, handled: boolean = true): Promise<{ success: boolean; message: string }> {
  const url = `${API_BASE_URL}/admin/violations/${id}/handled?handled=${handled}`;
  const response = await fetch(url, {
    method: "PUT",
    headers: { Accept: "application/json" },
  });

  if (!response.ok) {
    throw new Error(`Mark violation handled request failed: ${response.status}`);
  }

  return response.json() as Promise<{ success: boolean; message: string }>;
}

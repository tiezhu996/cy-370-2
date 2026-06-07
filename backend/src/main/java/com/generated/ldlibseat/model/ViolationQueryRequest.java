package com.generated.ldlibseat.model;

import java.time.LocalDate;

public class ViolationQueryRequest {
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer page = 0;
  private Integer size = 20;
  private String sortBy = "violationTime";
  private String sortDirection = "DESC";

  public ViolationQueryRequest() {}

  public LocalDate getStartDate() { return startDate; }
  public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
  public LocalDate getEndDate() { return endDate; }
  public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
  public Integer getPage() { return page; }
  public void setPage(Integer page) { this.page = page; }
  public Integer getSize() { return size; }
  public void setSize(Integer size) { this.size = size; }
  public String getSortBy() { return sortBy; }
  public void setSortBy(String sortBy) { this.sortBy = sortBy; }
  public String getSortDirection() { return sortDirection; }
  public void setSortDirection(String sortDirection) { this.sortDirection = sortDirection; }
}

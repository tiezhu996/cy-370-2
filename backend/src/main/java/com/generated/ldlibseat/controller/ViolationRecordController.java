package com.generated.ldlibseat.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generated.ldlibseat.model.PageResponse;
import com.generated.ldlibseat.model.ViolationQueryRequest;
import com.generated.ldlibseat.model.ViolationRecord;
import com.generated.ldlibseat.model.ViolationUserStats;
import com.generated.ldlibseat.service.ViolationRecordService;

@RestController
@RequestMapping({"/api/admin/violations", "/admin/violations"})
public class ViolationRecordController {
  private final ViolationRecordService violationRecordService;

  public ViolationRecordController(ViolationRecordService violationRecordService) {
    this.violationRecordService = violationRecordService;
  }

  @GetMapping
  public PageResponse<ViolationRecord> getViolationRecords(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size,
      @RequestParam(defaultValue = "violationTime") String sortBy,
      @RequestParam(defaultValue = "DESC") String sortDirection) {

    ViolationQueryRequest request = new ViolationQueryRequest();
    request.setStartDate(startDate);
    request.setEndDate(endDate);
    request.setPage(page);
    request.setSize(size);
    request.setSortBy(sortBy);
    request.setSortDirection(sortDirection);

    return violationRecordService.getViolationRecords(request);
  }

  @GetMapping("/stats")
  public List<ViolationUserStats> getUserViolationStats(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

    ViolationQueryRequest request = new ViolationQueryRequest();
    request.setStartDate(startDate);
    request.setEndDate(endDate);

    return violationRecordService.getUserViolationStats(request);
  }

  @PutMapping("/{id}/handled")
  public String markAsHandled(@PathVariable Long id, @RequestParam(defaultValue = "true") boolean handled) {
    boolean success = violationRecordService.markAsHandled(id, handled);
    if (success) {
      return "{\"success\": true, \"message\": \"操作成功\"}";
    } else {
      return "{\"success\": false, \"message\": \"操作失败，记录不存在\"}";
    }
  }
}

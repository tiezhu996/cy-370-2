package com.generated.ldlibseat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.generated.ldlibseat.model.PageResponse;
import com.generated.ldlibseat.model.ViolationQueryRequest;
import com.generated.ldlibseat.model.ViolationRecord;
import com.generated.ldlibseat.model.ViolationUserStats;
import com.generated.ldlibseat.repository.ViolationRecordRepository;

@Service
public class ViolationRecordService {
  private final ViolationRecordRepository violationRecordRepository;

  public ViolationRecordService(ViolationRecordRepository violationRecordRepository) {
    this.violationRecordRepository = violationRecordRepository;
  }

  public PageResponse<ViolationRecord> getViolationRecords(ViolationQueryRequest request) {
    int offset = request.getPage() * request.getSize();
    List<ViolationRecord> records = violationRecordRepository.findByDateRange(
      request.getStartDate(),
      request.getEndDate(),
      offset,
      request.getSize(),
      request.getSortBy(),
      request.getSortDirection()
    );
    long total = violationRecordRepository.countByDateRange(
      request.getStartDate(),
      request.getEndDate()
    );
    int totalPages = (int) Math.ceil((double) total / request.getSize());

    return new PageResponse<>(
      records,
      total,
      totalPages,
      request.getPage(),
      request.getSize()
    );
  }

  public List<ViolationUserStats> getUserViolationStats(ViolationQueryRequest request) {
    return violationRecordRepository.findUserViolationStats(
      request.getStartDate(),
      request.getEndDate()
    );
  }

  public boolean markAsHandled(Long id, boolean handled) {
    return violationRecordRepository.updateHandled(id, handled) > 0;
  }
}

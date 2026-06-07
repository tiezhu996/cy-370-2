package com.generated.ldlibseat.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ViolationRecord {
  private Long id;
  private Long userId;
  private String userName;
  private Long reservationId;
  private Long seatId;
  private String seatCode;
  private String violationType;
  private LocalDateTime violationTime;
  private LocalDate reservationDate;
  private LocalTime startTime;
  private LocalTime endTime;
  private String description;
  private Boolean handled;
  private LocalDateTime createdAt;

  public ViolationRecord() {}

  public ViolationRecord(Long id, Long userId, String userName, Long reservationId, Long seatId, String seatCode,
      String violationType, LocalDateTime violationTime, LocalDate reservationDate, LocalTime startTime,
      LocalTime endTime, String description, Boolean handled, LocalDateTime createdAt) {
    this.id = id;
    this.userId = userId;
    this.userName = userName;
    this.reservationId = reservationId;
    this.seatId = seatId;
    this.seatCode = seatCode;
    this.violationType = violationType;
    this.violationTime = violationTime;
    this.reservationDate = reservationDate;
    this.startTime = startTime;
    this.endTime = endTime;
    this.description = description;
    this.handled = handled;
    this.createdAt = createdAt;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getUserId() { return userId; }
  public void setUserId(Long userId) { this.userId = userId; }
  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
  public Long getReservationId() { return reservationId; }
  public void setReservationId(Long reservationId) { this.reservationId = reservationId; }
  public Long getSeatId() { return seatId; }
  public void setSeatId(Long seatId) { this.seatId = seatId; }
  public String getSeatCode() { return seatCode; }
  public void setSeatCode(String seatCode) { this.seatCode = seatCode; }
  public String getViolationType() { return violationType; }
  public void setViolationType(String violationType) { this.violationType = violationType; }
  public LocalDateTime getViolationTime() { return violationTime; }
  public void setViolationTime(LocalDateTime violationTime) { this.violationTime = violationTime; }
  public LocalDate getReservationDate() { return reservationDate; }
  public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
  public LocalTime getStartTime() { return startTime; }
  public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
  public LocalTime getEndTime() { return endTime; }
  public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public Boolean getHandled() { return handled; }
  public void setHandled(Boolean handled) { this.handled = handled; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

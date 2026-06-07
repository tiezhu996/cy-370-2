package com.generated.ldlibseat.model;

import java.time.LocalDateTime;

public class Seat {
  private Long id;
  private String seatCode;
  private Integer floor;
  private String area;
  private String seatType;
  private Boolean hasPower;
  private Boolean isAvailable;
  private LocalDateTime createdAt;

  public Seat() {}

  public Seat(Long id, String seatCode, Integer floor, String area, String seatType, Boolean hasPower, Boolean isAvailable, LocalDateTime createdAt) {
    this.id = id;
    this.seatCode = seatCode;
    this.floor = floor;
    this.area = area;
    this.seatType = seatType;
    this.hasPower = hasPower;
    this.isAvailable = isAvailable;
    this.createdAt = createdAt;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getSeatCode() { return seatCode; }
  public void setSeatCode(String seatCode) { this.seatCode = seatCode; }
  public Integer getFloor() { return floor; }
  public void setFloor(Integer floor) { this.floor = floor; }
  public String getArea() { return area; }
  public void setArea(String area) { this.area = area; }
  public String getSeatType() { return seatType; }
  public void setSeatType(String seatType) { this.seatType = seatType; }
  public Boolean getHasPower() { return hasPower; }
  public void setHasPower(Boolean hasPower) { this.hasPower = hasPower; }
  public Boolean getIsAvailable() { return isAvailable; }
  public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

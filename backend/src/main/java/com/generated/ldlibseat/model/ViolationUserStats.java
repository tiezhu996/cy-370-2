package com.generated.ldlibseat.model;

public class ViolationUserStats {
  private Long userId;
  private String userName;
  private Integer violationCount;

  public ViolationUserStats() {}

  public ViolationUserStats(Long userId, String userName, Integer violationCount) {
    this.userId = userId;
    this.userName = userName;
    this.violationCount = violationCount;
  }

  public Long getUserId() { return userId; }
  public void setUserId(Long userId) { this.userId = userId; }
  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
  public Integer getViolationCount() { return violationCount; }
  public void setViolationCount(Integer violationCount) { this.violationCount = violationCount; }
}

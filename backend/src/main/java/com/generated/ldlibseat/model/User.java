package com.generated.ldlibseat.model;

import java.time.LocalDateTime;

public class User {
  private Long id;
  private String username;
  private String realName;
  private String email;
  private String phone;
  private String role;
  private LocalDateTime createdAt;

  public User() {}

  public User(Long id, String username, String realName, String email, String phone, String role, LocalDateTime createdAt) {
    this.id = id;
    this.username = username;
    this.realName = realName;
    this.email = email;
    this.phone = phone;
    this.role = role;
    this.createdAt = createdAt;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public String getRealName() { return realName; }
  public void setRealName(String realName) { this.realName = realName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

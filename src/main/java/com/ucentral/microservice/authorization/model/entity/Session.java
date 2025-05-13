package com.ucentral.microservice.authorization.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
public class Session implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "account_id", nullable = false)
  private Long accountId;

  @Column(name = "login_status", nullable = false)
  private Byte loginStatus;

  @Column(name = "access_token", nullable = false)
  private String accessToken;

  @Column(name = "ip_address", nullable = false)
  private String ipAddress;

  @Column(name = "device_used", nullable = false)
  private String deviceUsed;

  @Column(name = "login_time", nullable = false)
  private LocalDateTime loginTime;

  @Column(name = "logout_time")
  private LocalDateTime logoutTime;

}

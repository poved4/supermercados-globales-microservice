package com.ucentral.microservice.authorization.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignInRequest {

  @Email
  @NotNull
  @NotBlank
  private String email;

  @NotNull
  @NotBlank
  private String password;

  @NotNull
  @NotBlank
  private String ipAddress;

  @NotNull
  @NotBlank
  private String deviceUsed;

}

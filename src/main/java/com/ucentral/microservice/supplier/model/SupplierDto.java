package com.ucentral.microservice.supplier.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SupplierDto(

    @NotNull
    @NotBlank
    String name,

    @Email
    @NotNull
    @NotBlank
    String email,

    @NotNull
    @NotBlank
    String phone,

    @NotNull
    @NotBlank
    String address

) {
}
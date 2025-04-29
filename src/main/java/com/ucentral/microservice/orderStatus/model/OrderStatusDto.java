package com.ucentral.microservice.orderStatus.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderStatusDto(

    @NotNull
    @NotBlank
    String name

) { }

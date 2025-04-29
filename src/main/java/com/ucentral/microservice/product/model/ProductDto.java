package com.ucentral.microservice.product.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductDto(

    @NotNull
    @NotBlank
    String code,

    @NotNull
    @NotBlank
    String name,

    @NotNull
    @NotBlank
    String description,

    @NotNull
    @Positive
    Long supplier,

    @NotNull
    @Positive
    Long category

) { }

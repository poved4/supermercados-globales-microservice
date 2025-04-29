package com.ucentral.microservice.productCategory.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCategoryDto(

  @NotNull
  @NotBlank
  String name

) { }

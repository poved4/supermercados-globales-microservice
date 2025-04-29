package com.ucentral.microservice.productCategory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucentral.microservice.productCategory.model.ProductCategoryDto;
import com.ucentral.microservice.productCategory.service.ProductCategoryService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products/categories")
public class ProductCategoryController {

  private final ProductCategoryService service;

  @GetMapping
  public ResponseEntity<?> getEndpoint() {
    return ResponseEntity.ofNullable(
      service.findAll()
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getEndpoint(@PathVariable @Positive Long id) {
    return ResponseEntity.ok(
      service.findById(id)
    );
  }

  @PostMapping
  public ResponseEntity<?> postEndpoint(@RequestBody @Valid ProductCategoryDto request) {
    return ResponseEntity.ok(
      service.save(request)
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> putEndpoint(@PathVariable @Positive Long id, @RequestBody @Valid ProductCategoryDto request) {
    return ResponseEntity.ok(
      service.update(id, request)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteEndpoint(@PathVariable @Positive Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

}

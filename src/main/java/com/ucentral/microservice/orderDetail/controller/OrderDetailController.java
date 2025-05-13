package com.ucentral.microservice.orderDetail.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucentral.microservice.orderDetail.service.OrderDetailsService;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderDetailController {

  private final OrderDetailsService orderDetailsService;

  @GetMapping("/{id}/details")
  public ResponseEntity<?> endPointOrderDetails(@PathVariable @Positive Long id) {
    var orders = orderDetailsService.getByOrderId(id);
    return ResponseEntity.ok(orders);
  }

}

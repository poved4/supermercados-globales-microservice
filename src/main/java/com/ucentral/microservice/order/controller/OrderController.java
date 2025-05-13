package com.ucentral.microservice.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucentral.microservice.order.model.UpdateOrderStatusDto;
import com.ucentral.microservice.order.service.OrderService;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  public ResponseEntity<?> endpoint() {
    return ResponseEntity.ok(orderService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> endpointOrder(@PathVariable @Positive Long id) {
    return ResponseEntity.ok(orderService.getById(id));
  }

  @GetMapping("/branch/{id}")
  public ResponseEntity<?> endpoint(@PathVariable @Positive Long id) {
    return ResponseEntity.ok(orderService.getByBranchId(id));
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<?> updateOrderStatus(@PathVariable @Positive Long id, @RequestBody UpdateOrderStatusDto request) {
    var updatedOrder = orderService.updateStatus(id, request.statusId());
    return ResponseEntity.ok(updatedOrder);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteEndpoint(@PathVariable @Positive Long id) {
    orderService.delete(id);
    return ResponseEntity.noContent().build();
  }

}

package com.ucentral.microservice.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ucentral.microservice.exc.model.BadRequestException;
import com.ucentral.microservice.exc.model.NoContentException;
import com.ucentral.microservice.order.model.Order;
import com.ucentral.microservice.order.repository.OrderRepository;
import com.ucentral.microservice.orderStatus.model.OrderStatus;
import com.ucentral.microservice.orderStatus.repository.OrderStatusRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderStatusRepository orderStatusRepository;

  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  public Order getById(Long id) {

    var entity = orderRepository.findById(id);

    if (!entity.isPresent()) {
      throw new NoContentException("Order with id " + id + " does not exist");
    }

    return entity.get();

  }

  public List<Order> getByBranchId(Long branchId) {

    var entity = orderRepository.findByBranchId(branchId);

    if (entity.isEmpty()) {
      throw new NoContentException("Order with id " + branchId + " does not exist");
    }

    return entity;

  }

  public Order updateStatus(Long orderId, Long statusId) {

    var orderOpt = orderRepository.findById(orderId);
    if (orderOpt.isEmpty()) {
      throw new NoContentException("Order with id " + orderId + " does not exist");
    }

    Optional<OrderStatus> currentStatus = orderStatusRepository.findById(statusId);
    if (!currentStatus.isPresent()) {
      throw new NoContentException("OrderStatus with id " + statusId + " does not exist");
    }

    Order order = orderOpt.get();
    OrderStatus newStatus = currentStatus.get();

    order.setStatusId(newStatus);

    if (newStatus.getName().toUpperCase().equals("ENTREGADO")) {
      order.setDeliveryDate(LocalDateTime.now());
    }

    Order updatedOrder = orderRepository.save(order);

    log.info("Updated status of order {} to {}", orderId, newStatus.getName());
    return updatedOrder;

  }

  public void delete(Long id) {

    if (!orderRepository.existsById(id)) {
      log.info("Product Category not found with id: {}", id);
      throw new BadRequestException("Product Category not found with id: " + id);
    }

    orderRepository.deleteById(id);

  }

}

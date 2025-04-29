package com.ucentral.microservice.orderStatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucentral.microservice.orderStatus.model.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> { }

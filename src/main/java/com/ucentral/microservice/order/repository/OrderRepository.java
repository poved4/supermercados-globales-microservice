package com.ucentral.microservice.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucentral.microservice.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByBranchId(Long branchId);

}

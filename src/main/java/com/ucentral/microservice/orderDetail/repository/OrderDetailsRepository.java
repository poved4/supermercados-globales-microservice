package com.ucentral.microservice.orderDetail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucentral.microservice.orderDetail.model.OrderDetail;


public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {

  List<OrderDetail> findByOrderId(Long orderId);

}

package com.ucentral.microservice.orderDetail.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucentral.microservice.exc.model.NoContentException;
import com.ucentral.microservice.orderDetail.model.OrderDetail;
import com.ucentral.microservice.orderDetail.repository.OrderDetailsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderDetailsService {

  private final OrderDetailsRepository orderDetailsRepository;

  public List<OrderDetail> getByOrderId(Long orderId) {

    var entity = orderDetailsRepository.findByOrderId(orderId);

    if (entity.isEmpty()) {
      throw new NoContentException("Order with id " + orderId + " does not exist");
    }

    return entity;

  }

}

package com.ucentral.microservice.orderStatus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucentral.microservice.exc.model.BadRequestException;
import com.ucentral.microservice.exc.model.NoContentException;
import com.ucentral.microservice.orderStatus.model.OrderStatus;
import com.ucentral.microservice.orderStatus.model.OrderStatusDto;
import com.ucentral.microservice.orderStatus.repository.OrderStatusRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusService {

  private final OrderStatusRepository repository;

  public List<OrderStatus> findAll() {

    var entities = repository.findAll();

    if (entities.isEmpty()) {
      throw new NoContentException();
    }

    return entities;

  }

  public OrderStatus findById(Long id) {

    var entity = repository.findById(id);

    if (!entity.isPresent()) {
      log.info("Order status with id {} does not exist", id);
      throw new BadRequestException("Order status with id " + id + " does not exist");
    }

    return entity.get();

  }

  public OrderStatus save(OrderStatus entity) {
    return repository.save(entity);
  }

  public OrderStatus save(OrderStatusDto dto) {
    return save(
      OrderStatus.builder()
      .name(dto.name())
      .build()
    );
  }

  public OrderStatus update(Long id, OrderStatusDto dto) {

    return repository
      .findById(id)
      .map(entity -> {
        entity.setName(dto.name());
        return save(entity);
      })
      .orElseThrow(() ->
        new BadRequestException("Order status not found with id: " + id)
      );

  }

  public void delete(Long id) {

    if (!repository.existsById(id)) {
      log.info("Product Category not found with id: {}", id);
      throw new BadRequestException("Product Category not found with id: " + id);
    }

    repository.deleteById(id);

  }

}

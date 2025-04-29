package com.ucentral.microservice.supplier.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucentral.microservice.exc.model.BadRequestException;
import com.ucentral.microservice.supplier.model.Supplier;
import com.ucentral.microservice.supplier.model.SupplierDto;
import com.ucentral.microservice.supplier.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierService {

  private final SupplierRepository repository;

  public List<Supplier> findAll() {
    return repository.findAll();
  }

  public Supplier findById(Long id) {

    var entity = repository.findById(id);

    if (!entity.isPresent()) {
      log.debug("Supplier with id {} does not exist", id);
      throw new BadRequestException("Supplier with id " + id + " does not exist");
    }

    return entity.get();

  }

  public Supplier save(Supplier branch) {
    return repository.save(branch);
  }

  public Supplier save(SupplierDto dto) {
    return save(
      Supplier.builder()
      .name(dto.name())
      .email(dto.email())
      .phone(dto.phone())
      .address(dto.address())
      .build()
    );
  }

  public Supplier update(Long id, SupplierDto dto) {

    return repository
      .findById(id)
      .map(entity -> {
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setPhone(dto.phone());
        entity.setAddress(dto.address());
        return save(entity);
      })
      .orElseThrow(() ->
        new BadRequestException("Branch not found with id: " + id)
      );

  }

  public void delete(Long id) {

    if (!repository.existsById(id)) {
      log.info("Branch not found with id: {}", id);
      throw new BadRequestException("Branch not found with id: " + id);
    }

    repository.deleteById(id);

  }

}

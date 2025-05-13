package com.ucentral.microservice.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucentral.microservice.exc.model.NoContentException;
import com.ucentral.microservice.inventory.model.Inventory;
import com.ucentral.microservice.inventory.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryRepository inventoryRepository;

    public List<Inventory> getByBranchId(Long id) {

    var inventory = inventoryRepository.findByBranchId(id);

    if (inventory.isEmpty()) {
      throw new NoContentException("Order with id " + id + " does not exist");
    }

    return inventory;

  }

}

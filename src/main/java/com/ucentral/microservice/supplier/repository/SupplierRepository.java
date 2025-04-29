package com.ucentral.microservice.supplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucentral.microservice.supplier.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> { }

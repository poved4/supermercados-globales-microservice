package com.ucentral.microservice.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucentral.microservice.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> { }

package com.ucentral.microservice.productCategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucentral.microservice.productCategory.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> { }

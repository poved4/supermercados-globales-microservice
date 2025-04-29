package com.ucentral.microservice.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucentral.microservice.exc.model.BadRequestException;
import com.ucentral.microservice.exc.model.NoContentException;
import com.ucentral.microservice.product.model.Product;
import com.ucentral.microservice.product.model.ProductDto;
import com.ucentral.microservice.product.repository.ProductRepository;
import com.ucentral.microservice.productCategory.service.ProductCategoryService;
import com.ucentral.microservice.supplier.service.SupplierService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repository;
  private final SupplierService supplierService;
  private final ProductCategoryService productCategoryService;

  public List<Product> findAll() {
    var entities = repository.findAll();

    if (entities.isEmpty()) {
      throw new NoContentException();
    }

    return entities;
  }

  public Product findById(Long id) {

    var entity = repository.findById(id);

    if (!entity.isPresent()) {
      log.info("Product Category with id {} does not exist", id);
      throw new BadRequestException("Product Category with id " + id + " does not exist");
    }

    return entity.get();

  }

  public Product save(Product entity) {
    return repository.save(entity);
  }

  public Product save(ProductDto request) {

    var supplier = supplierService.findById(request.supplier());
    var category = productCategoryService.findById(request.category());

    return save(
      Product.builder()
        .code(request.code())
        .name(request.name())
        .description(request.description())
        .supplier(supplier)
        .category(category)
        .build()
    );

  }

  public Product update(Long id, ProductDto dto) {

    return repository
        .findById(id)
        .map(entity -> {

          var supplier = supplierService.findById(dto.supplier());
          var category = productCategoryService.findById(dto.category());

          entity.setCode(dto.code());
          entity.setName(dto.name());
          entity.setDescription(dto.description());
          entity.setSupplier(supplier);
          entity.setCategory(category);

          return save(entity);

        })
        .orElseThrow(() ->
          new BadRequestException("Product Category not found with id: " + id)
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

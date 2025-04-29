package com.ucentral.microservice.productCategory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucentral.microservice.exc.model.BadRequestException;
import com.ucentral.microservice.productCategory.model.ProductCategory;
import com.ucentral.microservice.productCategory.model.ProductCategoryDto;
import com.ucentral.microservice.productCategory.repository.ProductCategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCategoryService {

  private final ProductCategoryRepository repository;

  public List<ProductCategory> findAll() {
    return repository.findAll();
  }

  public ProductCategory findById(Long id) {

    var entity = repository.findById(id);

    if (!entity.isPresent()) {
      log.debug("Product Category with id {} does not exist", id);
      throw new BadRequestException("Product Category with id " + id + " does not exist");
    }

    return entity.get();

  }

  public ProductCategory save(ProductCategory category) {
    return repository.save(category);
  }

  public ProductCategory save(ProductCategoryDto request) {
    return save(
      ProductCategory.builder()
        .name(request.name())
        .build()
    );
  }

  public ProductCategory update(Long id, ProductCategoryDto dto) {

    return repository
    .findById(id)
    .map(entity -> {
      entity.setName(dto.name());
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

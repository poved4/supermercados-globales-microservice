package com.ucentral.microservice.branch.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucentral.microservice.branch.model.Branch;
import com.ucentral.microservice.branch.model.BranchDto;
import com.ucentral.microservice.branch.repository.BranchRepository;
import com.ucentral.microservice.exc.model.BadRequestException;
import com.ucentral.microservice.exc.model.NoContentException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchService {

  private final BranchRepository repository;

  public List<Branch> findAll() {
    return repository.findAll();
  }

  public Branch findById(Long id) {

    var entity = repository.findById(id);

    if (!entity.isPresent()) {
      log.debug("Branch with id {} does not exist", id);
      throw new NoContentException("Branch with id " + id + " does not exist");
    }

    return entity.get();

  }

  public Branch save(Branch branch) {
    return repository.save(branch);
  }

  public Branch save(BranchDto branch) {
    return save(
      Branch.builder()
      .name(branch.name())
      .address(branch.address())
      .build()
    );
  }

  public Branch update(Long id, BranchDto branchDto) {

    return repository
      .findById(id)
      .map(entity -> {
        entity.setName(branchDto.name());
        entity.setAddress(branchDto.address());
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

package com.ucentral.microservice.branch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucentral.microservice.branch.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> { }

package com.ucentral.microservice.authorization.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ucentral.microservice.authorization.model.entity.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

  public Optional<Account> findByEmail(String email);

}

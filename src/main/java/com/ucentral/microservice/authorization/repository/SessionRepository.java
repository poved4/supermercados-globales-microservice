package com.ucentral.microservice.authorization.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ucentral.microservice.authorization.model.entity.Session;

public interface SessionRepository extends CrudRepository<Session, Long> {

    public Optional<Session> findByAccessToken(String accessToken);

    public Optional<Session> findByAccountIdAndLoginStatus(Long accountId, Byte loginStatus);

}

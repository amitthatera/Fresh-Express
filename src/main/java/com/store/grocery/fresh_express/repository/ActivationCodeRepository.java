package com.store.grocery.fresh_express.repository;

import com.store.grocery.fresh_express.model.ActivationCode;
import com.store.grocery.fresh_express.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {

    Optional<ActivationCode> findByToken(String token);

    Optional<ActivationCode> findByUser(User user);
}

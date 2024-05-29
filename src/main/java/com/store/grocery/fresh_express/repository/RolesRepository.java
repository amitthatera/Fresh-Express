package com.store.grocery.fresh_express.repository;

import com.store.grocery.fresh_express.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByRoleName(String name);
}

package com.store.grocery.fresh_express.repository;

import com.store.grocery.fresh_express.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAddressIgnoreCase(String email);

    List<User> findByFirstNameContainingIgnoreCase(String firstName);
}

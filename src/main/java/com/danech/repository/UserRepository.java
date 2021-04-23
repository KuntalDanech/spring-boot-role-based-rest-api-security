package com.danech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danech.model.User;
/**
 * JPA Repository
 * Dynamic proxy will be used to create its implementation object
 * @author dev77
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserName(String userName);
}
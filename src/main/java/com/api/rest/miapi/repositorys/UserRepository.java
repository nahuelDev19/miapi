package com.api.rest.miapi.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.miapi.entitys.User;



public interface UserRepository extends JpaRepository<User,Long>{

    boolean existsByUsername(String name);
    Optional<User> findByUsername(String username);
}

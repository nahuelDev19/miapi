package com.api.rest.miapi.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.miapi.entitys.Role;


public interface RoleRepository extends JpaRepository<Role,Long>{


    Optional<Role> findByName(String name);

}

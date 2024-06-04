package com.api.rest.miapi.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.miapi.entitys.UserApi;



public interface UserRepository extends JpaRepository<UserApi,Long>{

    Optional<UserApi> findByUsername(String username);

}

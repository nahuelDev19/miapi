package com.api.rest.miapi.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.miapi.entitys.UserApi;

public interface UserRepository extends JpaRepository<UserApi,Long>{

}

package com.api.rest.miapi.repositorys;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.api.rest.miapi.entitys.UserApi;



public interface UserRepository extends JpaRepository<UserApi,Long>{

    Optional<UserApi> findByUsername(String username);

    //@Query("select u from UserApi u where u.username=?1")
    //Boolean existByUsername(String name);
    boolean existsByUsername(String username);

  

}

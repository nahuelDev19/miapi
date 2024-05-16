package com.api.rest.miapi.services;

import java.util.List;

import com.api.rest.miapi.entitys.User;

public interface UserService {


    List<User> findAll();
    User save(User user);
    boolean existsByUsername(String name);

}

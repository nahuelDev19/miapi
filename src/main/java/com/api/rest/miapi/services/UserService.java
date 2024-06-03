package com.api.rest.miapi.services;

import java.util.List;

import com.api.rest.miapi.entitys.UserApi;

public interface UserService {


    List<UserApi> findAll();
    UserApi save(UserApi userApi);

}

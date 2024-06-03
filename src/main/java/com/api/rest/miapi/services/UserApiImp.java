package com.api.rest.miapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rest.miapi.entitys.UserApi;
import com.api.rest.miapi.repositorys.UserRepository;

@Service
public class UserApiImp implements UserService{


    @Autowired
    private UserRepository repository;


    @Transactional(readOnly = true)
    @Override
    public List<UserApi> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public UserApi save(UserApi userApi) {
        return repository.save(userApi);
    }

    





}

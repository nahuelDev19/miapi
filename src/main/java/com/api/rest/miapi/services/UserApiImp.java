package com.api.rest.miapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rest.miapi.entitys.Role;
import com.api.rest.miapi.entitys.UserApi;
import com.api.rest.miapi.repositorys.RoleRepository;
import com.api.rest.miapi.repositorys.UserRepository;

@Service
public class UserApiImp implements UserService{


    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    @Override
    public List<UserApi> findAll() {
        List<UserApi> todos= repository.findAll();
        for(UserApi upi: todos){
            boolean isAdmin= upi.getRoles().stream().anyMatch(us-> us.getName().equals("ROLE_ADMIN"));
            upi.setAdmin(isAdmin);
        }
        return todos;
    }

    @Transactional
    @Override
    public UserApi save(UserApi userApi) {

        Optional<Role> isUser= roleRepository.findByName("ROLE_USER");
        if (!isUser.isPresent()) {
            throw new RuntimeException("ROLE_USER no fue encontrado");
        }
        List<Role> roles= new ArrayList<>();
        isUser.ifPresent(roles::add);

        if (userApi.isAdmin()) {
            Optional<Role> idAdmin= roleRepository.findByName("ROLE_ADMIN");
            
            if (!idAdmin.isPresent()) {
                throw new RuntimeException("ROLE_ADMIN no fue encontrado");
            }
            idAdmin.ifPresent(roles::add);
        }

        userApi.setRoles(roles);
        userApi.setPassword(passwordEncoder.encode(userApi.getPassword()));

        return repository.save(userApi);
    }

    





}

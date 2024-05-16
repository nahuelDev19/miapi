package com.api.rest.miapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rest.miapi.entitys.Role;
import com.api.rest.miapi.entitys.User;
import com.api.rest.miapi.repositorys.RoleRepository;
import com.api.rest.miapi.repositorys.UserRepository;

@Service
public class UserServiceImp implements UserService{


    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder pass;


    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) repo.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        Optional<Role> optionalRole= roleRepo.findByName("ROLE_USER");
        List<Role> roles= new ArrayList<>();


        optionalRole.ifPresent(roles::add);
        if(user.isAdmin()){
            Optional<Role> optionalRoleAdmin= roleRepo.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        user.setRoles(roles);
        user.setPassword(pass.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    public boolean existsByUsername(String name) {
        return repo.existsByUsername(name);
    }

}

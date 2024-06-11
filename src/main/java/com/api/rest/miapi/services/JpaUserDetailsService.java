package com.api.rest.miapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rest.miapi.entitys.UserApi;
import com.api.rest.miapi.repositorys.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserApi> userOptional= userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s no existe en bd", username));
        }
        UserApi userApi= userOptional.orElseThrow();
        List<GrantedAuthority> authorities= userApi.getRoles().stream()
        .map(upi-> new SimpleGrantedAuthority(upi.getName()))
        .collect(Collectors.toList());


        return new org.springframework.security.core.userdetails.User(userApi.getUsername(),
        userApi.getPassword(),
        true,
        true,
        true,
        true,
         authorities);
    }

}

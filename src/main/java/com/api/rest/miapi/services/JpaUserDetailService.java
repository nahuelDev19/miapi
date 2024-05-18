package com.api.rest.miapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rest.miapi.entitys.User;
import com.api.rest.miapi.repositorys.UserRepository;

@Service
public class JpaUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional= userRepository.findByUsername(username);
        
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("username %s no encontrado en el sistema", username));
        }

        User user= userOptional.orElseThrow();

        List<GrantedAuthority> authorities= user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(),
        user.isEnabled(),
        true,
        true,
        true,
        authorities);
        
    }

}

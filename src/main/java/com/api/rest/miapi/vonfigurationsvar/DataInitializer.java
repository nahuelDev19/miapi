package com.api.rest.miapi.vonfigurationsvar;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.api.rest.miapi.entitys.Role;
import com.api.rest.miapi.entitys.UserApi;
import com.api.rest.miapi.repositorys.RoleRepository;
import com.api.rest.miapi.repositorys.UserRepository;
@Component
public class DataInitializer implements CommandLineRunner{

     @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @Override
    public void run(String... args) throws Exception {
        Role userRole = roleRepository.findByName("ROLE_USER").orElse(new Role("ROLE_USER"));
        roleRepository.save(userRole);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(new Role("ROLE_ADMIN"));
        roleRepository.save(adminRole);

         // Lista de usuarios para insertar
         List<UserApi> users = Arrays.asList(
            new UserApi("nahuel", "1234", Arrays.asList(userRole)),
            new UserApi("camila", "6543", Arrays.asList(userRole)),
            new UserApi("jesus", "010101", Arrays.asList(adminRole))
        );

        for(UserApi user: users){
            if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
                
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                if (user.getRoles().contains(adminRole)) {
                    user.setAdmin(true);
                }
                userRepository.save(user);
            }
        }

        
}


}

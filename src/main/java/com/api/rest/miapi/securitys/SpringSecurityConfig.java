package com.api.rest.miapi.securitys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {


    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    SecurityFilterChain security(HttpSecurity http)throws Exception{
        return http.authorizeHttpRequests( (authz) -> authz
        .requestMatchers(HttpMethod.GET,"/users").permitAll()
        .requestMatchers(HttpMethod.POST,"/users/register").permitAll()
        .anyRequest().authenticated())
        .addFilter(new JwtAuthenticationManager( authenticationConfiguration.getAuthenticationManager()))
        .addFilter(new JwtValidationToken( authenticationConfiguration.getAuthenticationManager()))
        .csrf(con-> con.disable())
        .sessionManagement(manag -> manag.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
    }


}

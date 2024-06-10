package com.api.rest.miapi.securitys;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.rest.miapi.entitys.UserApi;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationManager extends UsernamePasswordAuthenticationFilter{


    private AuthenticationManager authenticationManager;

    

    public JwtAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager= authenticationManager;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
                UserApi userApi= null;
                String username= null;
                String password= null;


                try {
                    userApi= new ObjectMapper().readValue(request.getInputStream(), UserApi.class);
                    username= userApi.getUsername();
                    password= userApi.getPassword();
                } catch (StreamReadException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (DatabindException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, password);
                return authenticationManager.authenticate(authenticationToken);

    }

    

}

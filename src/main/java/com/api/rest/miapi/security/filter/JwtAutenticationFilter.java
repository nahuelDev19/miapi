package com.api.rest.miapi.security.filter;

import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.rest.miapi.entitys.User;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.spi.STEUtil;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAutenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    private static final Key SECRET_KEY=  Jwts.SIG.HS256.key().build();


    public JwtAutenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager= authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        User user= null;
        String username= null;
        String password = null;

        try {
            user= new ObjectMapper().readValue(request.getInputStream(), User.class);
            username= user.getUsername();
            password= user.getPassword();
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

        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username,
         password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

                org.springframework.security.core.userdetails.User user= (org.springframework.security.core.userdetails.User)authResult.getPrincipal();
                String username=user.getUsername();
                String token = Jwts.builder().subject(username).signWith(SECRET_KEY).compact();

                response.addHeader("Authorization ", "Bearer "+ token);

                Map<String,String> body= new HashMap<>();
                body.put("token", token);
                body.put("username", username);
                body.put("message", String.format("hola %s has ingresasado con exito", username));

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));;
                response.setContentType("application/json");;
                response.setStatus(200);


    }

    
    


    



}

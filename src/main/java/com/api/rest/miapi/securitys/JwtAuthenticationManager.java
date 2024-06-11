package com.api.rest.miapi.securitys;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.rest.miapi.entitys.UserApi;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static com.api.rest.miapi.securitys.TokenJwtConfig.*;

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
                    e.printStackTrace();
                } catch (DatabindException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, password);
                return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

                org.springframework.security.core.userdetails.User userApi= (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
                String username= userApi.getUsername();

                Collection<? extends GrantedAuthority> roles= authResult.getAuthorities();
                
                Claims claims= Jwts.claims()
                .add("rol",roles)
                .add("username", username)
                .build();
                


                String token= Jwts.builder()
                .subject(username)
                .claim("claims", claims)
                .signWith(SECRET_KEY)
                .expiration(new Date(System.currentTimeMillis()+36000000))
                .issuedAt(new Date())
                .compact();

                response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN+ token);
                Map<String ,String> body= new HashMap<>();
                body.put("token", token);
                body.put("username",username);
                body.put("message", String.format("hola %s, has iniciado sesion con exito", username));

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setContentType(CONTEN_TYPE);
                response.setStatus(200);
    }



    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
                Map<String ,String> body= new HashMap<>();
                body.put("error", "el nombre o el password es incorrecto");
                body.put("error",failed.getMessage());
                
                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setContentType(CONTEN_TYPE);
                response.setStatus(401);

        
    }

    
    

    

}

package com.api.rest.miapi.securitys;

import static com.api.rest.miapi.securitys.TokenJwtConfig.CONTEN_TYPE;
import static com.api.rest.miapi.securitys.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.api.rest.miapi.securitys.TokenJwtConfig.PREFIX_TOKEN;
import static com.api.rest.miapi.securitys.TokenJwtConfig.SECRET_KEY;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationToken extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtValidationToken.class);

    public JwtValidationToken(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.replace(PREFIX_TOKEN, "");
        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            //String username = claims.getSubject();
            String username= (String) claims.get("username");
            Object authoritiesClaims = claims.get("rol");

            // Logging para depuración
            logger.debug("Token: {}", token);
            logger.debug("Claims: {}", claims);
            logger.debug("Username: {}", username);
            logger.debug("AuthoritiesClaims: {}", authoritiesClaims);

            // Verificar si authoritiesClaims es nulo
            if (authoritiesClaims == null) {
                logger.error("Authorities claims are null");
                chain.doFilter(request, response);
                return;
            }

            Collection<? extends GrantedAuthority   > authorities = Arrays.asList(
                new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGratedAuthoritiJsonCreator.class)
                .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token JWT es inválido");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401);
            response.setContentType(CONTEN_TYPE);
        }
    }
}

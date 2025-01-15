package com.example.raha.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizeFilter extends OncePerRequestFilter {
    private final AntPathRequestMatcher matcher = new AntPathRequestMatcher("login");

    @Autowired
    @Value("${jwt.secret}")
    private String secret;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!matcher.matches(request)) {
            String xAuthToken = request.getHeader("X-AUTH-TOKEN");
            if (xAuthToken == null || !xAuthToken.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build()
                    .verify(xAuthToken.substring(7));
            String username = decodedJWT.getClaim("username").toString();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
        }
        filterChain.doFilter(request, response);
    }
}

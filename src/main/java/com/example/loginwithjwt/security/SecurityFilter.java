package com.example.loginwithjwt.security;

import com.example.loginwithjwt.jwt.Jwtutill;
import com.example.loginwithjwt.service.UserDetailServicesImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private Jwtutill jwtutill;
    @Autowired
    private UserDetailServicesImpl utill;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String username = null;
        String Token = null;
            String authorizationHeader = request.getHeader("Authorization");


            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                Token = authorizationHeader.substring(7);
                try{
                    username = jwtutill.getSubject(Token);
                }catch (Exception e)
                {
                    System.out.println("Error Extracting username " + e.getMessage());
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = utill.loadUserByUsername(username);
                    boolean isValid = jwtutill.isValidToken(Token, userDetails.getUsername());
                    if (isValid) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(username,
                                        userDetails.getPassword(),
                                        userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    }
                }

        filterChain.doFilter(request, response);

    }
}

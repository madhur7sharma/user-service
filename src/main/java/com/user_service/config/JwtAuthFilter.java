package com.user_service.config;

import com.user_service.service.CustomUserDetailsService;
import com.user_service.service.JwtService;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null, email = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtService.extractEmail(token);

//            System.out.println("email= " + jwtService.extractEmail(token));
//            System.out.println("userId= " + jwtService.extractUserId(token));
//            System.out.println(url.getFile());
        }
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;

            try {
                userDetails = customUserDetailsService.loadUserByUsername(email);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                if(jwtService.validateToken(token, userDetails) && isUserAuthorized(request, token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isUserAuthorized(HttpServletRequest request, String token) throws URISyntaxException, MalformedURLException {
//        URL url = new URI(request.getRequestURL().toString()).toURL();
//        String substring = url.getFile().substring(1);
//        String[] split = url.getFile().substring(1).split("/");
//        if(split[1].equals(jwtService.extractUserId(token))) {
//            return true;
//        }
//        return false;
        return true;
    }
}

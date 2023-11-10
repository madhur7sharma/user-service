package com.user_service.service;

import com.user_service.config.CustomUserDetails;
import com.user_service.entity.User;
import com.user_service.respository.user.IUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRespository.findByEmail(email);
        return new CustomUserDetails(user);
    }
}

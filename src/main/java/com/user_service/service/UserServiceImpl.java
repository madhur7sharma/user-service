package com.user_service.service;


import com.user_service.entity.User;
import com.user_service.respository.user.IUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRespository userRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwt;

    @Override
    public String generateToken(String email) {
        return jwt.generateToken(email);
    }

//    @Override
//    public boolean validateToken(String token) {
//        jwt.validateToken(token);
//    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRespository.save(user);
        return user;
    }
}

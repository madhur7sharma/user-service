package com.user_service.service;


import com.user_service.entity.User;
import com.user_service.respository.user.IUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRespository userRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwt;

    @Override
    public String generateToken(User user) {
        return jwt.generateToken(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRespository.findAll();
    }

    @Override
    public User validateUser(User user) {
        User userByEmail = userRespository.findByEmail(user.getEmail());
        if(userByEmail != null) {
            if(passwordEncoder.matches(user.getPassword(), userByEmail.getPassword())) {
                return userByEmail;
            }
            return null;
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        return userRespository.findById(id).get();
    }

    @Override
    public User findByEmail(String email) {
        return userRespository.findByEmail(email);
    }

    @Override
    public User findByUserName(String userName) {
        return userRespository.findByUserName(userName);
    }

    @Override
    public List<User> findByNameContaining(String name) {
        return userRespository.findByFirstNameContainingAllIgnoreCase(name);
    }

//    @Override
//    public boolean validateToken(String token) {
//        jwt.validateToken(token);
//    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("NORMAL");
        userRespository.save(user);
        return user;
    }

    @Override
    public User editUser(Long userId, User user) {
        User exitingUserDetails = userRespository.findById(userId).get();
        user.setPassword(exitingUserDetails.getPassword());
        user.setEmail(exitingUserDetails.getEmail());
        user.setUserName(exitingUserDetails.getUserName());
        user.setRoles("NORMAL");
        return userRespository.save(user);
    }
}

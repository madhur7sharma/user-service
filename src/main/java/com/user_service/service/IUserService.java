package com.user_service.service;

import com.user_service.entity.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user);
    String generateToken(User user);
//    boolean validateToken(String token);
    List<User> getAllUsers();

    User validateUser(User user);

    User findByEmail(String email);
}

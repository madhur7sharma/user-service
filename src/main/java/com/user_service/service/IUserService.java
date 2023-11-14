package com.user_service.service;

import com.user_service.entity.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user);
    String generateToken(String email);
//    boolean validateToken(String token);
    List<User> getAllUsers();
}

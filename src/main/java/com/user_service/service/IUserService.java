package com.user_service.service;

import com.user_service.entity.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user);
    User editUser(Long userId, User user);
    String generateToken(User user);
//    boolean validateToken(String token);
    List<User> getAllUsers();

    User validateUser(User user);

    User findById(Long id);

    User findByEmail(String email);

    User findByUserName(String userName);

    List<User> findByNameContaining(String name);

    boolean checkIfEmailAlreadyRegistered(String email);

    boolean checkIfUsernameAlreadyRegistered(String userName);
}

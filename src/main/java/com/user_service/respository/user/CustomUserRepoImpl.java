package com.user_service.respository.user;

import com.user_service.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomUserRepoImpl implements ICustomUserRepo {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public User registerUser(User user) {
        entityManager.persist(user);
        return user;
    }
}

package com.user_service.respository.user;

import com.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRespository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUserName(String userName);

    List<User> findByFirstNameContainingAllIgnoreCase(@Param("name") String name);
}

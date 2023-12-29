package com.user_service.respository.post;

import com.user_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin("http://localhost:4200")
public interface IPostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostByUserId(@Param("userId") Long userId);

    List<Post> findPostByUserUserName(@Param("userName") String userName);
}

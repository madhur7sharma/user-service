package com.user_service.respository.comment;

import com.user_service.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin("http://localhost:4200")
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByPostId(@Param("postId") Long postId);
};

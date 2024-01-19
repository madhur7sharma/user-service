package com.user_service.respository.comment;

import com.user_service.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin("http://localhost:4200")
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT comments FROM Comment comments WHERE post.id =:postId AND nestedCommentId IS NULL")
    List<Comment> findCommentByPostId(@Param("postId") Long postId);

    @Query("SELECT comments FROM Comment comments WHERE nestedCommentId =:commentId")
    List<Comment> findNestedCommentByCommentId(@Param("commentId") Long commentId);

    @Modifying
    @Transactional
    void deleteByPostId(@Param("postId") Long postId);
};

package com.user_service.respository.comment;

import com.user_service.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("http://localhost:4200")
public interface ICommentRepository extends JpaRepository<Comment, Long> {
}

package com.user_service.service;

import com.user_service.entity.Comment;
import com.user_service.respository.comment.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceImpl implements ICommentService {

    @Autowired
    private ICommentRepository commentRepository;

    @Override
    public Comment addComment(Comment comment) {
        commentRepository.save(comment);
        return comment;
    }
}

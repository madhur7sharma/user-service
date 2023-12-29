package com.user_service.service;

import com.user_service.entity.Comment;

import java.util.List;

public interface ICommentService {
    Comment addComment(Comment comment);

    List<Comment> findCommentsByPostId(Long postId);
}

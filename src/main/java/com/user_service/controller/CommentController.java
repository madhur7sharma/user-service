package com.user_service.controller;

import com.user_service.entity.Comment;
import com.user_service.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/post/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @PostMapping("/")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        Comment addComment = commentService.addComment(comment);
        return new ResponseEntity<>(addComment, HttpStatus.OK);
    }

}

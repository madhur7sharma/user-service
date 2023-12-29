package com.user_service.controller;

import com.user_service.dto.CommentTO;
import com.user_service.dto.converter.ICommentConverter;
import com.user_service.entity.Comment;
import com.user_service.entity.Post;
import com.user_service.entity.User;
import com.user_service.service.ICommentService;
import com.user_service.utilities.UserServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserServiceConstants.USER_BASE_ROUTE + "/post/{postId}/")
@CrossOrigin("http://localhost:4200")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<Comment> addComment(@PathVariable(value = "userId") Long userId, @PathVariable(value = "postId") Long postId, @RequestBody Comment comment) {
        User user = new User();
        user.setId(userId);

        Post post = new Post();
        post.setId(postId);

        comment.setUser(user);
        comment.setPost(post);

        Comment addComment = commentService.addComment(comment);
        return new ResponseEntity<>(addComment, HttpStatus.OK);
    }

    @GetMapping("/allcomments")
    public ResponseEntity<List<CommentTO>> getComments(@PathVariable(value = "postId") Long postId) {
        List<CommentTO> commentsByPostId = ICommentConverter.INSTANCE.convertToCommentTO(commentService.findCommentsByPostId(postId));
        return new ResponseEntity<>(commentsByPostId, HttpStatus.OK);
    }

}

package com.user_service.controller;

import com.user_service.dto.LikeRequest;
import com.user_service.dto.PostTO;
import com.user_service.dto.converter.PostConverter;
import com.user_service.entity.Post;
import com.user_service.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/post")
public class PostController {

    @Autowired
    private IPostService postService;

    @PostMapping("/")
    public ResponseEntity<PostTO> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return new ResponseEntity<>(PostConverter.INSTANCE.convertToPostTO(createdPost), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostTO> getPostById(@PathVariable(value = "postId") Long postId) {
        Post post = postService.getPostById(postId);
        PostTO postTO = PostConverter.INSTANCE.convertToPostTO(post);
        return new ResponseEntity<>(postTO, HttpStatus.OK);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<LikeRequest> likePost(@PathVariable(value = "postId") Long postId, @RequestBody LikeRequest likeRequest) {
        postService.likePost(postId, likeRequest.getUserId(), likeRequest.getAction());
        return new ResponseEntity<>(likeRequest, HttpStatus.OK);
    }
}

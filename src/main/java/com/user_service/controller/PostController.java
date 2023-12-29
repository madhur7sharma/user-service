package com.user_service.controller;

import com.user_service.dto.LikeRequest;
import com.user_service.dto.PostTO;
import com.user_service.dto.converter.PostConverter;
import com.user_service.entity.Post;
import com.user_service.entity.User;
import com.user_service.service.IPostService;
import com.user_service.utilities.UserServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserServiceConstants.USER_BASE_ROUTE + "/post")
@CrossOrigin("http://localhost:4200")
public class PostController {

    @Autowired
    private IPostService postService;

    @PostMapping("/")
    public ResponseEntity<PostTO> createPost(@PathVariable(value = "userId") Long userId, @RequestBody Post post) {
        User user = new User();
        user.setId(userId);
        post.setUser(user);
        Post createdPost = postService.createPost(post);
        return new ResponseEntity<>(PostConverter.INSTANCE.convertToPostTO(createdPost), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostTO> getPostById(@PathVariable(value = "postId") Long postId) {
        Post post = postService.getPostById(postId);
        PostTO postTO = PostConverter.INSTANCE.convertToPostTO(post);
        return new ResponseEntity<>(postTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostTO>> getAllPostsOfUser(@PathVariable(value = "userId") Long userId) {
        List<Post> postByUserId = postService.findPostByUserId(userId);
        List<PostTO> postTOS = PostConverter.INSTANCE.convertToPostTO(postByUserId);
        return new ResponseEntity<>(postTOS, HttpStatus.OK);
    }

    @GetMapping("/all/{username}")
    public ResponseEntity<List<PostTO>> getAllPostsOfUserName(@PathVariable(value = "username") String userName) {
        List<Post> postByUserId = postService.findPostByUserName(userName);
        List<PostTO> postTOS = PostConverter.INSTANCE.convertToPostTO(postByUserId);
        return new ResponseEntity<>(postTOS, HttpStatus.OK);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<LikeRequest> likePost(@PathVariable(value = "userId") Long userId, @PathVariable(value = "postId") Long postId, @RequestBody LikeRequest likeRequest) {
        likeRequest.setUserId(userId);
        postService.likePost(postId, likeRequest.getUserId(), likeRequest.getAction());
        return new ResponseEntity<>(likeRequest, HttpStatus.OK);
    }
}

package com.user_service.service;

import com.user_service.dto.LikeStates;
import com.user_service.entity.Post;

import java.util.List;

public interface IPostService {
    Post createPost(Post post);

    Post getPostById(Long postId);

    void likePost(Long postId, Long userId, LikeStates action);

    List<Post> findPostByUserId(Long userId);

    List<Post> findPostByUserName(String userName);

    List<Post> getTimelinePosts(Long userId);
}

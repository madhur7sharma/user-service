package com.user_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.user_service.entity.Comment;
import com.user_service.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PostTO {

    private Long id;

    private String postUrl;

    @JsonIgnoreProperties({"posts","likedPosts", "comments", "followers", "following"})
    private User user;

    @JsonIgnoreProperties({"posts", "likedPosts", "comments", "followers", "following"})
    @JsonIgnore
    private Set<User> likes = new HashSet<>();

    private int noOfLikes = 0;

    @JsonIgnoreProperties({"user", "post"})
    @JsonIgnore
    private Set<Comment> comments;

    private int noOfComments = 0;

}

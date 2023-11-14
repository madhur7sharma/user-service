package com.user_service.dto;

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

    @JsonIgnoreProperties("posts")
    private User user;

    private Set<User> likes = new HashSet<>();

    @JsonIgnoreProperties({"user","post"})
    private Set<Comment> comments;

}

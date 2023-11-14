package com.user_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.user_service.entity.Comment;
import com.user_service.entity.Following;
import com.user_service.entity.Post;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserTO {
    private Long id;

    private String userName;

    private String email;

    private String password;

    private boolean isPrivate = false;

    private String firstName;

    private String lastName;

    private int age;

    private Date dateOfBirth;

    private String roles;

    @JsonIgnoreProperties("user")
    private List<Post> posts;

    @JsonIgnore
    private Set<Post> likedPosts = new HashSet<>();

    @JsonIgnore
    private Set<Comment> comments;

    @JsonIgnore
    private Set<Following> following = new HashSet<>();

    private Date dateCreated;

    private Date lastUpdated;
}

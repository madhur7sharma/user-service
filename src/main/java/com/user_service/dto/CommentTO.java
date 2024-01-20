package com.user_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.user_service.entity.Post;
import com.user_service.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentTO {

    private Long id;

    private String comment;

    @JsonIgnoreProperties({"posts", "likedPosts", "comments", "followers", "following"})
    private User user;

    @JsonIncludeProperties({"id"})
    private Post post;

    private Date dateCreated;

    private Date lastUpdated;
}

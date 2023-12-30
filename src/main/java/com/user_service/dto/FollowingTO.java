package com.user_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.user_service.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FollowingTO {

    private Long id;

    private FollowStates followRequest;

    @JsonIgnoreProperties({"posts","likedPosts", "comments", "followers", "following"})
    private User user;

    private boolean isUserFollowedByLoggedInUser = false;

    private Date dateCreated;

    private Date lastUpdated;
}

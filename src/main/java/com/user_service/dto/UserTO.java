package com.user_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.user_service.entity.Comment;
import com.user_service.entity.Following;
import com.user_service.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserTO {
    private Long id;

    private String userName;

    private String email;

    private boolean isPrivate = false;

    private String firstName;

    private String lastName;

    private int age;

    private Date dateOfBirth;

    private String roles;

    private int noOfPosts = 0;

    private int noOfFollowing = 0;

    private int noOfFollowers = 0;

    private IsUserFollowing isUserFollowedByLoggedInUser = IsUserFollowing.NOT_FOLLOWING;

    private Date dateCreated;

    private Date lastUpdated;
}

package com.user_service.dto;

import lombok.Data;

@Data
public class FollowRequest {
    private Long followingId;
    private FollowAction action;
    private FollowStates request;
}

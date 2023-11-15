package com.user_service.service;


import com.user_service.dto.FollowRequest;

public interface IFollowingService {
    void updateFollowAction(Long userId, FollowRequest followRequest);
}

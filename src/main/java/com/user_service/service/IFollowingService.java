package com.user_service.service;


import com.user_service.dto.FollowRequest;
import com.user_service.entity.Following;

import java.util.List;

public interface IFollowingService {
    void updateFollowAction(Long userId, FollowRequest followRequest);

    List<Following> findFollowersByUserName(String userName);
}

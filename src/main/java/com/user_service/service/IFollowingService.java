package com.user_service.service;


import com.user_service.dto.FollowRequest;
import com.user_service.entity.Following;

import java.util.List;

public interface IFollowingService {
    Following updateFollowAction(Long userId, FollowRequest followRequest);

    List<Following> findFollowersByUserName(String userName);

    List<Following> getFollowRequests(Long userId);

    void updateRequestAction(Long userId, FollowRequest followRequest);
    List<Following> findFollowers(String userName);
    List<Following> findFollowing(String userName);
}

package com.user_service.service;

import com.user_service.entity.Following;
import com.user_service.entity.User;
import com.user_service.respository.following.IFollowingRepository;
import com.user_service.respository.user.IUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowingServiceImpl implements IFollowingService {

    @Autowired
    IFollowingRepository followingRepository;

    @Autowired
    private IUserRespository userRespository;

    @Override
    public void follow(Long userId, Long followingId) {
        User user = userRespository.findById(userId).get();
        User followingUser = userRespository.findById(followingId).get();
//        if(followingUser.) {
            Following following = new Following();
            following.getFollower().add(user);

            user.getFollowing().add(following);
            userRespository.save(user);
//        }

    }
}

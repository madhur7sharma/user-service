package com.user_service.service;

import com.user_service.entity.Following;
import com.user_service.entity.User;
import com.user_service.respository.following.IFollowingRepository;
import com.user_service.respository.user.IUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

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
        Following fol = new Following();
        fol.setFrom(user);
        fol.setTo(followingUser);
        boolean isAlreadyFollowing = false;
        Iterator<Following> namesIterator = user.getFollowing().iterator();
        while(namesIterator.hasNext()) {
            if(namesIterator.next().getTo().getId() == followingId) {
                isAlreadyFollowing = true;
            }
        }
        if(!isAlreadyFollowing) {
            followingRepository.save(fol);
        }
    }
}

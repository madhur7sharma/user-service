package com.user_service.service;

import com.user_service.dto.FollowAction;
import com.user_service.dto.FollowRequest;
import com.user_service.entity.Following;
import com.user_service.entity.User;
import com.user_service.respository.following.IFollowingRepository;
import com.user_service.respository.user.IUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class FollowingServiceImpl implements IFollowingService {

    @Autowired
    IFollowingRepository followingRepository;

    @Autowired
    private IUserRespository userRespository;

    @Override
    public void updateFollowAction(Long userId, FollowRequest followRequest) {
        User user = userRespository.findById(userId).get();
        User followingUser = userRespository.findById(followRequest.getFollowingId()).get();
        Following fol = new Following();
        fol.setFrom(user);
        fol.setTo(followingUser);
        boolean isAlreadyFollowing = false;
        Iterator<Following> namesIterator = user.getFollowing().iterator();
        while(namesIterator.hasNext()) {
            if(namesIterator.next().getTo().getId() == followRequest.getFollowingId()) {
                isAlreadyFollowing = true;
            }
        }
        if(followRequest.getAction().equals(FollowAction.FOLLOW)) {
            if(!isAlreadyFollowing) {
                followingRepository.save(fol);
            }
        } else {
            if(isAlreadyFollowing) {
                followingRepository.unFollowUser(fol.getFrom().getId(), fol.getTo().getId());
            }
        }
        // TODO refine this implementation\
    }

    @Override
    public List<Following> findFollowersByUserName(String userName) {
        return followingRepository.findFollowingByFromUserName(userName);
    }
}

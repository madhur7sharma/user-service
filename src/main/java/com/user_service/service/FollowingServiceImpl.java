package com.user_service.service;

import com.user_service.dto.FollowAction;
import com.user_service.dto.FollowRequest;
import com.user_service.dto.FollowStates;
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
    public Following updateFollowAction(Long userId, FollowRequest followRequest) {
        User user = userRespository.findById(userId).get();
        User followingUser = userRespository.findById(followRequest.getFollowingId()).get();
        Following fol = new Following();
        fol.setFrom(user);
        fol.setTo(followingUser);
        if(followingUser.isPrivate()) {
            fol.setFollowRequest(FollowStates.PENDING);
        } else {
            fol.setFollowRequest(FollowStates.ACCEPTED);
        }
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
        return fol;
        // TODO refine this implementation\
    }

    @Override
    public List<Following> findFollowersByUserName(String userName) {
        return followingRepository.findFollowingByFromUserName(userName);
    }

    @Override
    public List<Following> getFollowRequests(Long userId) {
        return followingRepository.getFollowRequests(userId);
    }

    @Override
    public void updateRequestAction(Long userId, FollowRequest followRequest) {
        Following following = followingRepository.findFollowingByFromAndTo(followRequest.getFollowingId(), userId);
        if(followRequest.getRequest().equals(FollowStates.ACCEPTED) && !following.getFollowRequest().equals(FollowStates.ACCEPTED)) {
            following.setFollowRequest(FollowStates.ACCEPTED);
            followingRepository.save(following);
        }
    }

    @Override
    public List<Following> findFollowers(String userName) {
        return followingRepository.findFollowers(userName);
    }

    @Override
    public List<Following> findFollowing(String userName) {
        return followingRepository.findFollowing(userName);
    }
}

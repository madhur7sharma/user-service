package com.user_service.dto.converter;

import com.user_service.dto.FollowStates;
import com.user_service.dto.FollowingTO;
import com.user_service.dto.IsUserFollowing;
import com.user_service.entity.Following;
import com.user_service.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface IFollowingConverter {

    IFollowingConverter INSTANCE = Mappers.getMapper(IFollowingConverter.class);

    @Mapping(source = "following", target = "user", qualifiedByName = "firstName")
    @Mapping(source = "following", target = "isUserFollowedByLoggedInUser", qualifiedByName = "isUserFollowedByLoggedInUser")
    FollowingTO convertToFollowingTO(Following following, @Context String type, @Context Set<Following> loggedInUserFollowing);

    @Named("firstName")
    default User firstName(Following following, @Context String type) {
        if(type.equals("followers")) {
            return following.getFrom();
        } else {
            return following.getTo();
        }
    }

    @Named("isUserFollowedByLoggedInUser")
    default IsUserFollowing isUserFollowedByLoggedInUser(Following loggedInUserFollower, @Context String type, @Context Set<Following> loggedInUserFollowing) {
        Following following1;
        if(type.equals("followers")) {
//            return loggedInUserFollowing.stream().anyMatch(follow -> follow.getTo().getId().equals(loggedInUserFollower.getFrom().getId()));
            following1 = loggedInUserFollowing.stream().filter(user1 -> user1.getTo().getId().equals(loggedInUserFollower.getFrom().getId())).findAny().orElse(null);
        } else {
//            return loggedInUserFollowing.stream().anyMatch(follow -> follow.getTo().getId().equals(loggedInUserFollower.getTo().getId()));
            following1 = loggedInUserFollowing.stream().filter(user1 -> user1.getTo().getId().equals(loggedInUserFollower.getTo().getId())).findAny().orElse(null);
        }
        if(following1 != null) {
            if(following1.getFollowRequest().equals(FollowStates.PENDING)) {
                return IsUserFollowing.REQUESTED;
            } else {
                return IsUserFollowing.FOLLOWING;
            }
        } else {
            return IsUserFollowing.NOT_FOLLOWING;
        }
    }

    default Set<FollowingTO> convertToFollowingTO(Collection<Following> followings, String type, Set<Following> loggedInUserFollowing) {
        return followings.stream().map(following -> {
            FollowingTO followingTO = this.convertToFollowingTO(following, type, loggedInUserFollowing);
            return followingTO;
        }).collect(Collectors.toSet());
    }

}

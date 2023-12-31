package com.user_service.dto.converter;

import com.user_service.dto.FollowingTO;
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
    @Mapping(source = "following", target = "userFollowedByLoggedInUser", qualifiedByName = "isUserFollowedByLoggedInUser")
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
    default boolean isUserFollowedByLoggedInUser(Following following, @Context String type, @Context Set<Following> loggedInUserFollowing) {
        if(type.equals("followers")) {
            return loggedInUserFollowing.stream().anyMatch(follow -> follow.getTo().getId().equals(following.getFrom().getId()));
        } else {
            return loggedInUserFollowing.stream().anyMatch(follow -> follow.getTo().getId().equals(following.getTo().getId()));
        }
    }

    default Set<FollowingTO> convertToFollowingTO(Collection<Following> followings, String type, Set<Following> loggedInUserFollowing) {
        return followings.stream().map(following -> {
            FollowingTO followingTO = this.convertToFollowingTO(following, type, loggedInUserFollowing);
            return followingTO;
        }).collect(Collectors.toSet());
    }

}

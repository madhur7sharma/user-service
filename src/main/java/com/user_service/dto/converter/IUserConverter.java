package com.user_service.dto.converter;

import com.user_service.dto.FollowStates;
import com.user_service.dto.IsUserFollowing;
import com.user_service.dto.UserTO;
import com.user_service.entity.Following;
import com.user_service.entity.Post;
import com.user_service.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface IUserConverter {

    IUserConverter INSTANCE = Mappers.getMapper(IUserConverter.class);

    @Mapping(source = "posts", target = "noOfPosts", qualifiedByName = "noOfPosts")
    @Mapping(source = "followers", target = "noOfFollowers", qualifiedByName = "noOfFollowers")
    @Mapping(source = "following", target = "noOfFollowing", qualifiedByName = "noOfFollowing")
    @Mapping(source = "user", target = "isUserFollowedByLoggedInUser", qualifiedByName = "isUserFollowedByLoggedInUser")
    UserTO convertToUserTO(User user, @Context Long loggedInUserId);

    @Named("noOfPosts")
    default int postNumbers(List<Post> posts) {
        return posts != null ? posts.size() : 0;
    }

    @Named("noOfFollowers")
    default int noOfFollowers(Set<Following> followers) {
        List<Following> followerNumber = followers.stream().filter(follower -> follower.getFollowRequest().equals(FollowStates.ACCEPTED)).collect(Collectors.toList());
        return followerNumber.size();
    }

    @Named("noOfFollowing")
    default int noOfFollowing(Set<Following> followings) {
        List<Following> followingNumber = followings.stream().filter(follower -> follower.getFollowRequest().equals(FollowStates.ACCEPTED)).collect(Collectors.toList());
        return followingNumber.size();
    }

    @Named("isUserFollowedByLoggedInUser")
    default IsUserFollowing isUserFollowedByLoggedInUser(User user, @Context Long loggedInUserId) {
        Following following = user.getFollowers().stream().filter(user1 -> user1.getFrom().getId().equals(loggedInUserId)).findAny().orElse(null);
        if(following != null) {
            if(following.getFollowRequest().equals(FollowStates.PENDING)) {
                return IsUserFollowing.REQUESTED;
            } else {
                return IsUserFollowing.FOLLOWING;
            }
        } else {
            return IsUserFollowing.NOT_FOLLOWING;
        }
    }

    User convertToUser(UserTO user);

    default List<UserTO> convertToUserTO(Collection<User> users, Long loggedInUserId) {
        return users.stream().map(user -> {
            UserTO userTO = this.convertToUserTO(user, loggedInUserId);
            return userTO;
        }).collect(Collectors.toList());
    }

}

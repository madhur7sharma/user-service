package com.user_service.dto.converter;

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
    @Mapping(source = "user", target = "userFollowedByLoggedInUser", qualifiedByName = "isUserFollowedByLoggedInUser")
    UserTO convertToUserTO(User user, @Context Long loggedInUserId);

    @Named("noOfPosts")
    default int postNumbers(List<Post> posts) {
        return posts.size();
    }

    @Named("noOfFollowers")
    default int noOfFollowers(Set<Following> followers) {
        return followers.size();
    }

    @Named("noOfFollowing")
    default int noOfFollowing(Set<Following> followings) {
        return followings.size();
    }

    @Named("isUserFollowedByLoggedInUser")
    default boolean isUserFollowedByLoggedInUser(User user, @Context Long loggedInUserId) {
        return user.getFollowers().stream().anyMatch(user1 -> user1.getFrom().getId().equals(loggedInUserId));
    }

    User convertToUser(UserTO user);

    default List<UserTO> convertToUserTO(Collection<User> users, Long loggedInUserId) {
        return users.stream().map(user -> {
            UserTO userTO = this.convertToUserTO(user, loggedInUserId);
            return userTO;
        }).collect(Collectors.toList());
    }

}

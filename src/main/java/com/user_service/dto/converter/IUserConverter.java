package com.user_service.dto.converter;

import com.user_service.dto.UserTO;
import com.user_service.entity.Following;
import com.user_service.entity.Post;
import com.user_service.entity.User;
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
    UserTO convertToUserTO(User user);

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

    default List<UserTO> convertToUserTO(Collection<User> users) {
        return users.stream().map(user -> {
            UserTO userTO = this.convertToUserTO(user);
            return userTO;
        }).collect(Collectors.toList());
    }

}

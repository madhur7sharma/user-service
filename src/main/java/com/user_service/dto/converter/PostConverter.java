package com.user_service.dto.converter;

import com.user_service.dto.PostTO;
import com.user_service.entity.Comment;
import com.user_service.entity.Post;
import com.user_service.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface PostConverter {

    PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

    @Mapping(source = "post.likes", target = "likedByUser", qualifiedByName = "likedByUser")
    @Mapping(source = "post.likes", target = "noOfLikes", qualifiedByName = "noOfLikes")
    @Mapping(source = "post.comments", target = "noOfComments", qualifiedByName = "noOfComments")
    PostTO  convertToPostTO(Post post, @Context Long currentUserId);

    @Named("noOfLikes")
    public static int noOfLikes(Set<User> users) {
        return users.size();
    }

    @Named("noOfComments")
    public static int noOfComments(Set<Comment> comments) {
        if(comments != null) {
            return comments.size();
        }
        return 0;
    }

    @Named("likedByUser")
    default boolean isLikedByUser(Set<User> users, @Context Long currentUserId) {
        boolean isLikedByUser = users.stream().anyMatch(user -> user.getId() == currentUserId);
        return isLikedByUser;
    }

    default List<PostTO> convertToPostTO(Collection<Post> posts, Long currentUserId) {
        if(posts != null) {
            return posts.stream().map(post -> {
                PostTO postTO = this.convertToPostTO(post, currentUserId);
                return postTO;
            }).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}

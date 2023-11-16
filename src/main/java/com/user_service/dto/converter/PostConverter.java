package com.user_service.dto.converter;

import com.user_service.dto.PostTO;
import com.user_service.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PostConverter {

    PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

    PostTO convertToPostTO(Post post);

    default List<PostTO> convertToPostTO(Collection<Post> posts) {
        return posts.stream().map(post -> {
            PostTO postTO = this.convertToPostTO(post);
            postTO.setNoOfComments(post.getComments().size());
            postTO.setNoOfLikes(post.getLikes().size());
            return postTO;
        }).collect(Collectors.toList());
    }
}

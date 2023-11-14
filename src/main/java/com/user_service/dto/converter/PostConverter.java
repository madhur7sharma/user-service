package com.user_service.dto.converter;

import com.user_service.dto.PostTO;
import com.user_service.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostConverter {

    PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

    PostTO convertToPostTO(Post post);
}

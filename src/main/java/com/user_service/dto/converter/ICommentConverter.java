package com.user_service.dto.converter;

import com.user_service.dto.CommentTO;
import com.user_service.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ICommentConverter {

    ICommentConverter INSTANCE = Mappers.getMapper(ICommentConverter.class);

    CommentTO convertToCommentTO(Comment comment);

    default List<CommentTO> convertToCommentTO(Collection<Comment> comments) {
        return comments.stream().map(comment -> {
            CommentTO commentTO = this.convertToCommentTO(comment);
            return commentTO;
        }).collect(Collectors.toList());
    }

}

package com.user_service.dto.converter;

import com.user_service.dto.UserTO;
import com.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserConverter {

    IUserConverter INSTANCE = Mappers.getMapper(IUserConverter.class);

    UserTO convertToUserTO(User user);
}

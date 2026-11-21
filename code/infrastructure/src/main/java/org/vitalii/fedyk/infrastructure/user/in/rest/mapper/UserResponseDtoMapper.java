package org.vitalii.fedyk.infrastructure.user.in.rest.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.application.user.dto.UserView;
import org.vitalii.fedyk.infrastructure.user.in.rest.dto.UserResponseDto;

@Mapper(componentModel = "spring")
public interface UserResponseDtoMapper {
  UserResponseDto toUserResponseDto(UserView userView);
}

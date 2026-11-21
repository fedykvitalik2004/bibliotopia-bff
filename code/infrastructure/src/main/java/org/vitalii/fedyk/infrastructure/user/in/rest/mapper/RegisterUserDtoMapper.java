package org.vitalii.fedyk.infrastructure.user.in.rest.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.application.user.dto.CreateUserCommand;
import org.vitalii.fedyk.infrastructure.user.in.rest.dto.RegisterUserDto;

@Mapper(componentModel = "spring")
public interface RegisterUserDtoMapper {
  CreateUserCommand toCommand(RegisterUserDto registerUserDto);
}

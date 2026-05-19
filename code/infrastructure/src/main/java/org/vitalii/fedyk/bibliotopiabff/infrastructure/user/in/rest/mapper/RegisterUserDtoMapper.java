package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.in.rest.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.CreateUserCommand;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.in.rest.dto.RegisterUserDto;

@Mapper(componentModel = "spring")
public interface RegisterUserDtoMapper {
  CreateUserCommand toCommand(RegisterUserDto registerUserDto);
}

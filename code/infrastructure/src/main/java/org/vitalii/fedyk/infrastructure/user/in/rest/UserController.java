package org.vitalii.fedyk.infrastructure.user.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vitalii.fedyk.application.user.dto.UserView;
import org.vitalii.fedyk.application.user.port.in.CreateUserUseCase;
import org.vitalii.fedyk.infrastructure.common.in.rest.dto.ErrorDto;
import org.vitalii.fedyk.infrastructure.generated.api.UsersApi;
import org.vitalii.fedyk.infrastructure.user.in.rest.dto.RegisterUserDto;
import org.vitalii.fedyk.infrastructure.user.in.rest.dto.UserResponseDto;
import org.vitalii.fedyk.infrastructure.user.in.rest.mapper.RegisterUserDtoMapper;
import org.vitalii.fedyk.infrastructure.user.in.rest.mapper.UserResponseDtoMapper;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "Endpoints for creating and managing users")
public class UserController implements UsersApi {
  private final CreateUserUseCase createUserUseCase;

  private final UserResponseDtoMapper userResponseDtoMapper;

  private final RegisterUserDtoMapper registerUserDtoMapper;

  @Operation(
      summary = "Create a new user",
      description =
          "Validates the request, creates a new user, and assigns default security privileges")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "User created successfully",
        content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Validation error: Check the response body for localized messages",
        content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    @ApiResponse(
        responseCode = "409",
        description = "Conflict error: User with this email already exists",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @Override
  public ResponseEntity<UserResponseDto> createUser(@RequestBody final RegisterUserDto request) {
    final var command = this.registerUserDtoMapper.toCommand(request);
    final UserView userView = this.createUserUseCase.create(command);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.userResponseDtoMapper.toUserResponseDto(userView));
  }
}

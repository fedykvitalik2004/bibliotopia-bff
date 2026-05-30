package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.in.rest;

import static org.vitalii.fedyk.bibliotopiabff.infrastructure.security.SecurityConstants.ACCESS_TOKEN_COOKIE_NAME;
import static org.vitalii.fedyk.bibliotopiabff.infrastructure.security.SecurityConstants.REFRESH_TOKEN_COOKIE_NAME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.UserView;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.in.GetUserUseCase;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.in.rest.dto.UserIdentityDto;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.provider.JwtProvider;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.util.CookieFactory;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(
    name = "Authentication",
    description = "Endpoints for managing user authentication and sessions")
public class AuthController {
  private final JwtProvider jwtProvider;

  private final GetUserUseCase getUserUseCase;

  @GetMapping("/me")
  @Operation(
      summary = "Get current user identity",
      description =
          "Retrieves information about the currently authenticated user based on the access token cookie")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "User identity retrieved successfully",
        content = @Content(schema = @Schema(implementation = UserIdentityDto.class))),
    @ApiResponse(
        responseCode = "401",
        description = "Unauthorized - Access token is missing or invalid")
  })
  public ResponseEntity<UserIdentityDto> getCurrentUserIdentity(
      @CookieValue(name = ACCESS_TOKEN_COOKIE_NAME, required = false) final String accessToken) {
    if (accessToken == null || !this.jwtProvider.isTokenValid(accessToken)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    final Long id = this.jwtProvider.extractUserId(accessToken);
    final List<String> roles = this.jwtProvider.extractRoles(accessToken);
    final List<String> permissions = this.jwtProvider.extractPermissions(accessToken);

    return ResponseEntity.ok(new UserIdentityDto(id, roles, permissions));
  }

  @PostMapping("/refresh") // todo: make like in enterprise systems
  @Operation(
      summary = "Refresh access token",
      description = "Generates a new access token using the refresh token from cookies")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Access token refreshed successfully"),
    @ApiResponse(
        responseCode = "403",
        description = "Forbidden - Refresh token is missing or invalid")
  })
  public ResponseEntity<Void> refreshAccessToken(
      @CookieValue(name = REFRESH_TOKEN_COOKIE_NAME, required = false) final String refreshToken) {
    if (refreshToken == null || !this.jwtProvider.isTokenValid(refreshToken)) {
      final ResponseCookie deleteAccessCookie = CookieFactory.createDeleteAccessCookie();
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .header(HttpHeaders.SET_COOKIE, deleteAccessCookie.toString())
          .build();
    }

    final Long userId = this.jwtProvider.extractUserId(refreshToken);

    final UserView userView = this.getUserUseCase.findUserById(userId);

    final String newAccessToken =
        this.jwtProvider.generateAccessToken(
            userView.id(), userView.roles(), userView.permissions());

    final ResponseCookie accessCookie =
        CookieFactory.createAccessCookie(newAccessToken, Duration.ofMinutes(15));

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessCookie.toString()).build();
  }
}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.in.rest.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.UserView;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.in.rest.dto.UserResponseDto;

class UserResponseDtoMapperTest {
  private final UserResponseDtoMapper mapper = new UserResponseDtoMapperImpl();

  @Test
  void shouldMapUserViewToUserResponseDto() {
    // Given
    final var userView =
        new UserView(
            1L,
            "Vitalii",
            "Fedyk",
            "vitalii.fedyk@example.com",
            LocalDate.of(2003, 5, 18),
            Instant.parse("2026-05-18T17:00:00Z"),
            Set.of("Customer"),
            Set.of("books:read", "books:write"));

    final UserResponseDto expectedResponse =
        new UserResponseDto(
            userView.id(),
            userView.firstName(),
            userView.lastName(),
            userView.email(),
            userView.birthDate(),
            userView.createdAt(),
            userView.roles(), // MapStruct автоматично збереже елементи
            userView.permissions());

    // When
    final var result = mapper.toUserResponseDto(userView);

    // Then
    assertEquals(expectedResponse, result);
  }
}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.in.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.in.rest.dto.RegisterUserDto;

class RegisterUserDtoMapperImplTest {
  private final RegisterUserDtoMapper mapper = new RegisterUserDtoMapperImpl();

  @Test
  void shouldMapRegisterUserDtoToCreateUserCommand() {
    // Given
    final var dto =
        new RegisterUserDto(
            "John",
            "Doe",
            "john.doe@example.com",
            "securePassword123",
            LocalDate.of(1995, 5, 15),
            "en");

    // When
    final var command = mapper.toCommand(dto);

    // Then
    assertThat(command).isNotNull();
    assertThat(command.firstName()).isEqualTo("John");
    assertThat(command.lastName()).isEqualTo("Doe");
    assertThat(command.email()).isEqualTo("john.doe@example.com");
    assertThat(command.rawPassword()).isEqualTo("securePassword123");
    assertThat(command.birthDate()).isEqualTo(LocalDate.of(1995, 5, 15));
    assertThat(command.language()).isEqualTo("en");
  }
}

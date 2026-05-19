package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Request body for registering a new user")
public record RegisterUserDto(
    @Schema(description = "User's first name", example = "Vitalii")
        @NotBlank(message = "First name is required")
        String firstName,
    @Schema(description = "User's last name", example = "Fedyk")
        @NotBlank(message = "Last name is required")
        String lastName,
    @Schema(description = "Unique electronic mail address", example = "vitalii.fedyk@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
    @Schema(description = "Raw, unhashed user password", example = "StrongP@ss123")
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String rawPassword,
    @Schema(description = "User's date of birth", example = "2003-05-18")
        @NotNull(message = "Birth date is required")
        LocalDate birthDate,
    @Schema(description = "Preferred interface language (ISO 639-1 alpha-2 code)", example = "uk")
        @NotBlank(message = "Language is required")
        String language) {}

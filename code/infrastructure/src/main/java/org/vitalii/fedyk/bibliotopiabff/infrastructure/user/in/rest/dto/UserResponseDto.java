package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Schema(description = "Represents user identity and security profile information")
public record UserResponseDto(
    @Schema(description = "Unique identifier of the user", example = "42") Long id,
    @Schema(description = "User's first name", example = "Vitalii") String firstName,
    @Schema(description = "User's last name", example = "Fedyk") String lastName,
    @Schema(description = "User's electronic mail address", example = "vitalii.fedyk@example.com")
        String email,
    @Schema(description = "User's date of birth", example = "2003-05-18") LocalDate birthDate,
    @Schema(
            description = "Timestamp when the user account was created",
            example = "2026-05-18T17:54:48Z")
        Instant createdAt,
    @Schema(
            description = "Set of roles assigned to the user",
            example = "[\"MEMBER\", \"PREMIUM_AUTHOR\"]")
        Set<String> roles,
    @Schema(
            description = "Set of distinct functional permissions granted to the user",
            example = "[\"books:read\", \"books:write\"]")
        Set<String> permissions) {}

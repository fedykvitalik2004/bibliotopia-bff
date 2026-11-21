package org.vitalii.fedyk.application.user.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;

@Builder
public record UserView(
    Long id,
    String firstName,
    String lastName,
    String email,
    LocalDate birthDate,
    Instant createdAt,
    Set<String> roles,
    Set<String> permissions) {}

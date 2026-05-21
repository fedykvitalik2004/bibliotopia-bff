package org.vitalii.fedyk.bibliotopiabff.application.user.dto;

import java.time.LocalDate;

public record CreateUserCommand(
    String firstName,
    String lastName,
    String email,
    String rawPassword,
    LocalDate birthDate,
    String language) {}

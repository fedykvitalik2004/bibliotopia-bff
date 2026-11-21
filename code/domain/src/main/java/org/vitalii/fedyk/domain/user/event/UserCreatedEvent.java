package org.vitalii.fedyk.domain.user.event;

import lombok.Builder;

@Builder
public record UserCreatedEvent(
    Long userId, String firstName, String lastName, String email, String language) {}

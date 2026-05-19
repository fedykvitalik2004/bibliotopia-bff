package org.vitalii.fedyk.bibliotopiabff.domain.user.event;

import lombok.Builder;

@Builder
public record UserCreatedEvent(
    Long userId, String firstName, String lastName, String email, String language) {}

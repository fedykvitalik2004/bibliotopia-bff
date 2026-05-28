package org.vitalii.fedyk.bibliotopiabff.application.security.dto;

import java.util.Set;

public record UserIdentityView(Long userId, Set<String> roles, Set<String> permissions) {}

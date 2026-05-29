package org.vitalii.fedyk.bibliotopiabff.application.user.dto;

import java.util.Set;

public record AccessNames(Set<String> roles, Set<String> permissions) {}

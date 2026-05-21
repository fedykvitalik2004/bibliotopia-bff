package org.vitalii.fedyk.bibliotopiabff.domain.security.model;

import java.util.Set;

public record Role(Long id, String name, Set<Long> permissionIds) {}

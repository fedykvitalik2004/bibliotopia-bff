package org.vitalii.fedyk.bibliotopiabff.domain.user.model;

import java.util.Set;

public record Role(String name, Set<Permission> permissions) {
}

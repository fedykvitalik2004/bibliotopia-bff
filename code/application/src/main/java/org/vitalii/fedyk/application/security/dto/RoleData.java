package org.vitalii.fedyk.application.security.dto;

import java.util.Set;

public record RoleData(Long roleId, String roleName, Set<String> permissionNames) {}

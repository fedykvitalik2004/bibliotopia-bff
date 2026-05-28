package org.vitalii.fedyk.bibliotopiabff.application.security.dto;

import java.util.Set;

public record UserPrivilegesView(Set<RoleView> roles, Set<PermissionView> permissions) {
  public record RoleView(Long id, String name) {}

  public record PermissionView(Long id, String name) {}
}

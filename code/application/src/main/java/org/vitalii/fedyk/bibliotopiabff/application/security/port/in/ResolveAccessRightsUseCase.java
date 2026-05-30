package org.vitalii.fedyk.bibliotopiabff.application.security.port.in;

import java.util.Set;
import org.vitalii.fedyk.bibliotopiabff.application.security.dto.PrivilegesView;

/** Use case to resolve the effective access rights for a user. */
public interface ResolveAccessRightsUseCase {
  /**
   * Aggregates all permissions from assigned roles and direct overrides.
   *
   * @param roleIds set of role IDs assigned to the user
   * @param directPermissionIds set of specific permissions granted directly to the user
   * @return {@link PrivilegesView} containing unique roles and combined permissions
   */
  PrivilegesView resolvePrivileges(final Set<Long> roleIds, final Set<Long> directPermissionIds);
}

package org.vitalii.fedyk.bibliotopiabff.application.security.port.in;

import java.util.Set;
import org.vitalii.fedyk.bibliotopiabff.application.security.dto.UserPrivilegesView;

/**
 * Use case to get user access and authorization details. It works with predefined static role
 * templates and direct user overrides configured in the system.
 */
public interface ResolveUserAccessRightsUseCase {
  /**
   * Fetches roles and aggregates both role-based permissions and direct permission overrides.
   *
   * @param roleIds a set of role IDs assigned to the user
   * @param directPermissionIds a set of permission IDs assigned directly to the user as overrides
   * @return a {@link UserPrivilegesView} containing the role details and all combined unique
   *     permissions
   */
  UserPrivilegesView resolvePrivileges(
      final Set<Long> roleIds, final Set<Long> directPermissionIds);
}

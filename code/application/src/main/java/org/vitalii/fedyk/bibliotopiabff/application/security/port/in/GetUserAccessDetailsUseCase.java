package org.vitalii.fedyk.bibliotopiabff.application.security.port.in;

import java.util.Set;
import org.vitalii.fedyk.bibliotopiabff.application.security.dto.UserAccessView;

/**
 * Use case to get user access and authorization details. It works with predefined static role
 * templates configured in the system.
 */
public interface GetUserAccessDetailsUseCase {
  /**
   * Fetches the roles and aggregates all their permissions by the given IDs.
   *
   * <p>This method takes a set of role IDs, finds the matching role templates, and combines all
   * their allowed permissions into a single {@link UserAccessView}.
   *
   * @param roleIds a set of role IDs assigned to the user
   * @return a {@link UserAccessView} containing the role details and all combined permissions
   */
  UserAccessView getUserAccessDetails(final Set<Long> roleIds);
}

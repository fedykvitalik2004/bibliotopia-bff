package org.vitalii.fedyk.application.security.port.out;

import java.util.List;
import java.util.Set;
import org.vitalii.fedyk.domain.security.model.Permission;

public interface PermissionRepository {
  List<Permission> findAllByRoleIds(Set<Long> roleIds);

  List<Permission> findAllByRoleIdsOrDirectIds(
      final Set<Long> roleIds, final Set<Long> directPermissionIds);
}

package org.vitalii.fedyk.bibliotopiabff.application.security.port.out;

import java.util.List;
import java.util.Set;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Permission;

public interface PermissionRepository {
  List<Permission> findAllByRoleIds(Set<Long> roleIds);

  List<Permission> findAllByRoleIdsOrDirectIds(
      final Set<Long> roleIds, final Set<Long> directPermissionIds);
}

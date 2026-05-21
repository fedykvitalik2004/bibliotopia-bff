package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.bibliotopiabff.application.auth.port.out.PermissionRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Permission;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence.entity.PermissionEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence.repository.PermissionJpaRepository;

@Repository
@AllArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {
  private final PermissionJpaRepository repository;

  @Override
  public List<Permission> findAllByRoleIds(final Set<Long> permissionIds) {
    return this.repository.findAllByRoleIds(permissionIds).stream()
        .map(PermissionRepositoryImpl::applyMapping)
        .toList();
  }

  private static Permission applyMapping(final PermissionEntity entity) {
    return new Permission(entity.getId(), entity.getName());
  }
}

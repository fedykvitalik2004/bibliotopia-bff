package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence.entity.PermissionEntity;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, Long> {
  @Query(
      """
        SELECT p
        FROM PermissionEntity p
        WHERE p.id IN (
            SELECT pid
            FROM RoleEntity r
            JOIN r.permissionIds pid
            WHERE r.id IN :roleIds
        )
    """)
  List<PermissionEntity> findAllByRoleIds(Set<Long> roleIds);
}

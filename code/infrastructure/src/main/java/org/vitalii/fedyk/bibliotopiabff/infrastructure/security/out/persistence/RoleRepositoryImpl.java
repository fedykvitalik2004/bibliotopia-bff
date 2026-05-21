package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.RoleRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Role;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence.entity.RoleEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence.repository.RoleJpaRepository;

@Repository
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
  private final RoleJpaRepository repository;

  @Override
  public Role getDefault() {
    return this.repository
        .findByName("Customer")
        .map(RoleRepositoryImpl::applyMapping)
        .orElseThrow(() -> new IllegalArgumentException("Default role not found"));
  }

  @Override
  public Optional<Role> findById(Long id) {
    return this.repository.findById(id).map(RoleRepositoryImpl::applyMapping);
  }

  @Override
  public List<Role> findAll() {
    return this.repository.findAll().stream().map(RoleRepositoryImpl::applyMapping).toList();
  }

  @Override
  public List<Role> findAllById(Set<Long> roleIds) {
    return this.repository.findAllById(roleIds).stream()
        .map(RoleRepositoryImpl::applyMapping)
        .toList();
  }

  private static Role applyMapping(final RoleEntity entity) {
    return new Role(entity.getId(), entity.getName(), entity.getPermissionIds());
  }
}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence.entity.RoleEntity;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
  Optional<RoleEntity> findByName(String name);
}

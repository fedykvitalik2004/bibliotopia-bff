package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
  boolean existsByEmail(String email);
}

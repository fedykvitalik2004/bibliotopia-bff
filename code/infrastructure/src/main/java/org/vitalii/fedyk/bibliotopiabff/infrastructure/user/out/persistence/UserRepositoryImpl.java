package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.UserRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.UserEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.mapper.UserMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.repository.UserJpaRepository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private UserJpaRepository repository;

  private UserMapper mapper;

  @Override
  public boolean existsByEmail(final Email email) {
    return this.repository.existsByEmail(email.value());
  }

  @Override
  public User save(final User user) {
    final UserEntity entity = this.mapper.toEntity(user);
    final UserEntity saved = this.repository.save(entity);
    return this.mapper.toDomain(saved);
  }
}

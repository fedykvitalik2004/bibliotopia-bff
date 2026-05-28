package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.UserRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.UserEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.mapper.UserMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.repository.UserJpaRepository;

@Repository
@AllArgsConstructor
@Transactional
public class UserRepositoryImpl implements UserRepository {
  private UserJpaRepository repository;

  private UserMapper mapper;

  @Override
  public boolean existsByEmail(final Email email) {
    return this.repository.existsByEmail(email.value());
  }

  @Override
  public Optional<User> findByEmail(Email email) {
    return this.repository.findByEmail(email.value()).map(this.mapper::toDomain);
  }

  @Override
  public Optional<User> findById(long id) {
    return this.repository.findById(id).map(this.mapper::toDomain);
  }

  @Override
  public User save(final User user) {
    final UserEntity entity = this.mapper.toEntity(user);
    final UserEntity saved = this.repository.save(entity);
    return this.mapper.toDomain(saved);
  }
}

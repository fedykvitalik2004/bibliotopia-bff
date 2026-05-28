package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.UserEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.mapper.UserMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.repository.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {
  @Mock private UserJpaRepository userJpaRepository;

  @Mock private UserMapper userMapper;

  @InjectMocks private UserRepositoryImpl repository;

  @Test
  void shouldReturnTrue_whenEmailExistsInDatabase() {
    // Given
    final var email = new Email(Instancio.create(String.class));

    when(this.userJpaRepository.existsByEmail(email.value())).thenReturn(true);

    // When
    final var result = this.repository.existsByEmail(email);

    // Then
    assertThat(result).isTrue();
    verify(this.userJpaRepository).existsByEmail(email.value());
  }

  @Test
  void shouldReturnUser_whenEmailExistsInDatabase() {
    // Given
    final var email = new Email(Instancio.create(String.class));
    final var entity = Instancio.create(UserEntity.class);
    final var domainUser = Instancio.create(User.class);

    when(this.userJpaRepository.findByEmail(email.value())).thenReturn(Optional.of(entity));
    when(this.userMapper.toDomain(entity)).thenReturn(domainUser);

    // When
    final var result = this.repository.findByEmail(email);

    // Then
    assertThat(result).isPresent().contains(domainUser);

    verify(this.userJpaRepository).findByEmail(email.value());
    verify(this.userMapper).toDomain(entity);
  }

  @Test
  void shouldReturnUser_whenIdExistsInDatabase() {
    // Given
    final long id = Instancio.create(Long.class);
    final var entity = Instancio.create(UserEntity.class);
    final var domainUser = Instancio.create(User.class);

    when(this.userJpaRepository.findById(id)).thenReturn(Optional.of(entity));
    when(this.userMapper.toDomain(entity)).thenReturn(domainUser);

    // When
    final var result = this.repository.findById(id);

    // Then
    assertThat(result).isPresent().contains(domainUser);

    verify(this.userJpaRepository).findById(id);
    verify(this.userMapper).toDomain(entity);
  }

  @Test
  void shouldSaveUserSuccessfully_whenUserIsProvided() {
    // Given
    final var userToSave = Instancio.create(User.class);
    final var initialEntity = Instancio.create(UserEntity.class);
    final var savedEntity = Instancio.create(UserEntity.class);
    final var expectedUser = Instancio.create(User.class);

    when(this.userMapper.toEntity(userToSave)).thenReturn(initialEntity);
    when(this.userJpaRepository.save(initialEntity)).thenReturn(savedEntity);
    when(this.userMapper.toDomain(savedEntity)).thenReturn(expectedUser);

    // When
    final var result = this.repository.save(userToSave);

    // Then
    assertThat(result).isNotNull().isEqualTo(expectedUser);

    verify(this.userMapper).toEntity(userToSave);
    verify(this.userJpaRepository).save(initialEntity);
    verify(this.userMapper).toDomain(savedEntity);
  }
}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Language;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.EncodedPassword;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.FullName;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.AuthProvider;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.FullNameEmbeddable;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.UserEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.UserStatus;

class UserMapperTest {
  private final UserMapper mapper = new UserMapperImpl();

  @Test
  void shouldMapUserToUserEntity_whenUserIsProvided() {
    // Given
    final var user =
        Instancio.of(User.class)
            .set(field(User::getLanguage), Language.EN)
            .set(field(User::getAuthProvider), User.AuthProvider.FACEBOOK)
            .set(field(User::getStatus), User.UserStatus.ACTIVE)
            .create();

    final var expected =
        UserEntity.builder()
            .id(user.getId())
            .fullName(
                new FullNameEmbeddable(
                    user.getFullName().firstName(), user.getFullName().lastName()))
            .email(user.getEmail().value())
            .encodedPassword(user.getEncodedPassword().value())
            .birthDate(user.getBirthDate())
            .language(user.getLanguage().getValue())
            .createdAt(user.getCreatedAt())
            .authProvider(AuthProvider.FACEBOOK)
            .status(UserStatus.ACTIVE)
            .roleIds(user.getRoleIds())
            .permissionIds(user.getPermissionIds());

    // When
    final var result = this.mapper.toEntity(user);

    // Then
    assertThat(result).isNotNull();
    assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldMapUserEntityToUser_whenEntityIsProvided() {
    // Given
    final var entity =
        Instancio.of(UserEntity.class)
            .set(field(UserEntity::getLanguage), "en")
            .set(field(UserEntity::getAuthProvider), AuthProvider.LOCAL)
            .set(field(UserEntity::getStatus), UserStatus.ACTIVE)
            .create();

    final var expectedFullName =
        new FullName(entity.getFullName().getFirstName(), entity.getFullName().getLastName());

    // When
    final var result = this.mapper.toDomain(entity);

    // Then
    assertThat(result)
        .isNotNull()
        .returns(entity.getId(), User::getId)
        .returns(new Email(entity.getEmail()), User::getEmail)
        .returns(new EncodedPassword(entity.getEncodedPassword()), User::getEncodedPassword)
        .returns(entity.getBirthDate(), User::getBirthDate)
        .returns(entity.getCreatedAt(), User::getCreatedAt)
        .returns(Language.EN, User::getLanguage)
        .returns(User.AuthProvider.LOCAL, User::getAuthProvider)
        .returns(User.UserStatus.ACTIVE, User::getStatus);

    assertThat(result.getFullName()).usingRecursiveComparison().isEqualTo(expectedFullName);

    assertThat(result.getRoleIds()).containsExactlyInAnyOrderElementsOf(entity.getRoleIds());

    assertThat(result.getPermissionIds())
        .containsExactlyInAnyOrderElementsOf(entity.getPermissionIds());
  }
}

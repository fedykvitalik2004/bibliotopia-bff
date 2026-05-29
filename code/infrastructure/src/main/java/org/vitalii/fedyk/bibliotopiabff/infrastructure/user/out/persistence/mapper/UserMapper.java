package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Language;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.EncodedPassword;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.FullName;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.AuthProvider;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.FullNameEmbeddable;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.UserEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.UserStatus;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "email", source = "email.value")
  @Mapping(target = "encodedPassword", source = "encodedPassword.value")
  @Mapping(target = "language", source = "language.value")
  UserEntity toEntity(User user);

  default User toDomain(final UserEntity entity) {
    if (entity == null) {
      return null;
    }

    final FullName fullName = this.toFullName(entity.getFullName());
    final Language language =
        entity.getLanguage() == null ? null : Language.fromCode(entity.getLanguage());
    final EncodedPassword encodedPassword =
        entity.getEncodedPassword() == null
            ? null
            : new EncodedPassword(entity.getEncodedPassword());
    final User.UserStatus status = this.toDomain(entity.getStatus());
    final User.AuthProvider authProvider = this.toDomain(entity.getAuthProvider());

    return User.restore(
        entity.getId(),
        fullName,
        new Email(entity.getEmail()),
        encodedPassword,
        entity.getBirthDate(),
        language,
        entity.getCreatedAt(),
        status,
        authProvider,
        entity.getRoleIds(),
        entity.getPermissionIds());
  }

  public abstract FullName toFullName(FullNameEmbeddable embeddable);

  UserStatus toEntity(User.UserStatus domainStatus);

  User.UserStatus toDomain(UserStatus entityStatus);

  AuthProvider toEntity(User.AuthProvider authProvider);

  User.AuthProvider toDomain(AuthProvider authProvider);
}

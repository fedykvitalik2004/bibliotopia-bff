package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.vitalii.fedyk.bibliotopiabff.domain.auth.model.EncodedPassword;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Language;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.FullName;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.FullNameEmbeddable;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "email", source = "email.value")
  @Mapping(target = "encodedPassword", source = "encodedPassword.value")
  @Mapping(target = "language", expression = "java(user.getLanguage().getValue().toLowerCase())")
  UserEntity toEntity(User user);

  default User toDomain(final UserEntity entity) {
    if (entity == null) {
      return null;
    }

    final FullName fullName = this.toFullName(entity.getFullName());

    return User.restore(
        entity.getId(),
        fullName,
        new Email(entity.getEmail()),
        new EncodedPassword(entity.getEncodedPassword()),
        entity.getBirthDate(),
        Language.fromCode(entity.getLanguage()),
        entity.getCreatedAt(),
        entity.getRoleIds(),
        entity.getPermissionIds());
  }

  FullName toFullName(FullNameEmbeddable embeddable);
}

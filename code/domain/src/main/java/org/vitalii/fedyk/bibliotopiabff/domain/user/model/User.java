package org.vitalii.fedyk.bibliotopiabff.domain.user.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Language;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {
  @Getter private Long id;
  private FullName fullName;
  private Email email;
  private EncodedPassword encodedPassword;
  private LocalDate birthDate;
  private Language language;
  private Instant createdAt;

  @Getter(AccessLevel.NONE)
  private Set<Long> roleIds;

  @Getter(AccessLevel.NONE)
  private Set<Long> permissionIds;

  public static User create(
      final FullName fullName,
      final Email email,
      final EncodedPassword encodedPassword,
      final LocalDate birthDate,
      Language language) {
    return new User(
        null,
        fullName,
        email,
        encodedPassword,
        birthDate,
        language,
        Instant.now(),
        new HashSet<>(),
        new HashSet<>());
  }

  public static User restore(
      final Long id,
      final FullName fullName,
      final Email email,
      final EncodedPassword encodedPassword,
      final LocalDate birthDate,
      final Language language,
      final Instant createdAt,
      final Set<Long> roles,
      final Set<Long> permissionIds) {
    return new User(
        id,
        fullName,
        email,
        encodedPassword,
        birthDate,
        language,
        createdAt,
        new HashSet<>(roles),
        new HashSet<>(permissionIds));
  }

  public void addRoleId(Long roleId) {
    this.roleIds.add(roleId);
  }

  public Set<Long> getRoleIds() {
    return Collections.unmodifiableSet(roleIds);
  }

  public Set<Long> getPermissionIds() {
    return Collections.unmodifiableSet(permissionIds);
  }
}

package org.vitalii.fedyk.bibliotopiabff.domain.user.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Language;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {
  @Getter private Long id;
  @Setter private FullName fullName;
  private Email email;
  private EncodedPassword encodedPassword;
  private LocalDate birthDate;
  private Language language;
  private Instant createdAt;
  @Getter private UserStatus status;
  @Getter private AuthProvider authProvider;

  @Getter(AccessLevel.NONE)
  private Set<Long> roleIds;

  @Getter(AccessLevel.NONE)
  private Set<Long> permissionIds;

  public enum UserStatus {
    PENDING_PROFILE_COMPLETION,
    ACTIVE
  }

  public enum AuthProvider {
    GOOGLE,
    FACEBOOK,
    LOCAL;

    public static AuthProvider fromString(final String providerId) {
      if (providerId == null) {
        return LOCAL;
      }
      return switch (providerId.toLowerCase()) {
        case "google" -> GOOGLE;
        case "facebook" -> FACEBOOK;
        default -> throw new IllegalArgumentException("Unknown identity provider: " + providerId);
      };
    }
  }

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
        UserStatus.ACTIVE,
        AuthProvider.LOCAL,
        new HashSet<>(),
        new HashSet<>());
  }

  public static User createPartial(
      final FullName fullName, final Email email, final AuthProvider authProvider) {
    return new User(
        null,
        fullName,
        email,
        null,
        null,
        null,
        Instant.now(),
        UserStatus.PENDING_PROFILE_COMPLETION,
        authProvider,
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
      final UserStatus status,
      final AuthProvider authProvider,
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
        status,
        authProvider,
        copyOfOrEmpty(roles),
        copyOfOrEmpty(permissionIds));
  }

  private static Set<Long> copyOfOrEmpty(final Set<Long> source) {
    return Optional.ofNullable(source).map(HashSet::new).orElseGet(HashSet::new);
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

package org.vitalii.fedyk.bibliotopiabff.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class User {
  private UUID id;
  private FullName fullName;
  private String email;
  private String passwordHash;
  private LocalDate birthDate;
  private String language;
  private Set<Role> roles;

  public boolean hasPermission(final String permissionName) {
    return this.roles.stream()
            .flatMap(role -> role.permissions().stream())
            .anyMatch(p -> p.name().equals(permissionName));
  }

  public Set<String> getAuthorities() {
    return roles.stream()
            .flatMap(role -> role.permissions().stream())
            .map(Permission::name)
            .collect(Collectors.toSet());
  }
}

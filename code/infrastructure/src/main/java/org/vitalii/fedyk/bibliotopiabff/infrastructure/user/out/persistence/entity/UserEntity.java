package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded private FullNameEmbeddable fullName;

  private String email;

  private String encodedPassword;

  private LocalDate birthDate;

  private String language;

  private Instant createdAt;

  @ElementCollection
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "role_id")
  private Set<Long> roleIds = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "permission_id")
  private Set<Long> permissionIds = new HashSet<>();
}

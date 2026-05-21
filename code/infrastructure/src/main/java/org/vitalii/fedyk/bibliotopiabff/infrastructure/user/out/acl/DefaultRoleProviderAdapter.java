package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.acl;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.PermissionRepository;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.RoleRepository;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.DefaultRoleProvider;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.RoleData;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Permission;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Role;

/**
 * ACL Adapter — the single point of convergence where the User domain and Security domain meet.
 * Responsibilities: 1. Call the Security domain via its own ports 2. Translate Security models
 * (Role, Permission) into the User ACL view (RoleData) Resides in infrastructure/user — belongs to
 * the User domain, but is permitted to have knowledge of the Security domain.
 */
@Component
@AllArgsConstructor
public class DefaultRoleProviderAdapter implements DefaultRoleProvider {
  private final RoleRepository roleRepository;

  private final PermissionRepository permissionRepository;

  @Override
  public RoleData getDefault() {
    final Role role = this.roleRepository.getDefault();
    final Set<String> permissionNames =
        this.permissionRepository.findAllByRoleIds(Set.of(role.id())).stream()
            .map(Permission::name)
            .collect(Collectors.toSet());
    return new RoleData(role.id(), role.name(), permissionNames);
  }
}

package org.vitalii.fedyk.bibliotopiabff.application.security.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vitalii.fedyk.bibliotopiabff.application.security.dto.PrivilegesView;
import org.vitalii.fedyk.bibliotopiabff.application.security.dto.RoleData;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.in.GetDefaultRoleUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.in.ResolveAccessRightsUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.PermissionRepository;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.RoleRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Permission;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Role;

@Service
@AllArgsConstructor
public class AuthService implements ResolveAccessRightsUseCase, GetDefaultRoleUseCase {
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  @Override
  @Transactional(readOnly = true)
  public PrivilegesView resolvePrivileges(
      final Set<Long> roleIds, final Set<Long> directPermissionIds) {
    final List<Role> roles = this.roleRepository.findAllById(roleIds);
    final Set<PrivilegesView.RoleView> roleViews =
        roles.stream()
            .map(role -> new PrivilegesView.RoleView(role.id(), role.name()))
            .collect(Collectors.toSet());

    // Fetch role-based permissions
    final List<Permission> permissions =
        this.permissionRepository.findAllByRoleIdsOrDirectIds(roleIds, directPermissionIds);
    final Set<PrivilegesView.PermissionView> permissionViews =
        permissions.stream()
            .map(
                permission -> new PrivilegesView.PermissionView(permission.id(), permission.name()))
            .collect(Collectors.toSet());

    return new PrivilegesView(roleViews, permissionViews);
  }

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

package org.vitalii.fedyk.bibliotopiabff.application.security.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vitalii.fedyk.bibliotopiabff.application.security.dto.UserIdentityView;
import org.vitalii.fedyk.bibliotopiabff.application.security.dto.UserPrivilegesView;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.in.ResolveUserAccessRightsUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.in.ResolveUserIdentityUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.LoadUserIdentityPort;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.PermissionRepository;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.RoleRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Permission;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Role;

@Service
@AllArgsConstructor
public class AuthService implements ResolveUserAccessRightsUseCase, ResolveUserIdentityUseCase {
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final LoadUserIdentityPort loadUserIdentityPort;

  @Override
  @Transactional(readOnly = true)
  public UserPrivilegesView resolvePrivileges(
      final Set<Long> roleIds, final Set<Long> directPermissionIds) {
    final List<Role> roles = this.roleRepository.findAllById(roleIds);
    final Set<UserPrivilegesView.RoleView> roleViews =
        roles.stream()
            .map(role -> new UserPrivilegesView.RoleView(role.id(), role.name()))
            .collect(Collectors.toSet());

    // Fetch role-based permissions
    final List<Permission> permissions =
        this.permissionRepository.findAllByRoleIdsOrDirectIds(roleIds, directPermissionIds);
    final Set<UserPrivilegesView.PermissionView> permissionViews =
        permissions.stream()
            .map(
                permission ->
                    new UserPrivilegesView.PermissionView(permission.id(), permission.name()))
            .collect(Collectors.toSet());

    return new UserPrivilegesView(roleViews, permissionViews);
  }

  @Override
  @Transactional(readOnly = true)
  public UserIdentityView getIdentity(final Long userId) {
    return this.loadUserIdentityPort.loadByUserId(userId);
  }
}

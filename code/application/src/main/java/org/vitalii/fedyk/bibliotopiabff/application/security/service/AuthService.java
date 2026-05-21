package org.vitalii.fedyk.bibliotopiabff.application.security.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vitalii.fedyk.bibliotopiabff.application.security.dto.UserAccessView;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.in.GetUserAccessDetailsUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.PermissionRepository;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.RoleRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Permission;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Role;

@Service
@AllArgsConstructor
public class AuthService implements GetUserAccessDetailsUseCase {
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  @Override
  @Transactional(readOnly = true)
  public UserAccessView getUserAccessDetails(final Set<Long> roleIds) {
    final List<Role> roles = this.roleRepository.findAllById(roleIds);
    final Set<UserAccessView.RoleView> roleViews =
        roles.stream()
            .map(role -> new UserAccessView.RoleView(role.id(), role.name()))
            .collect(Collectors.toSet());

    final List<Permission> permissions = this.permissionRepository.findAllByRoleIds(roleIds);
    final Set<UserAccessView.PermissionView> permissionViews =
        permissions.stream()
            .map(
                permission -> new UserAccessView.PermissionView(permission.id(), permission.name()))
            .collect(Collectors.toSet());

    return new UserAccessView(roleViews, permissionViews);
  }
}

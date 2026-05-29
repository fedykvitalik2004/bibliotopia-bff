package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.acl;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.bibliotopiabff.application.security.dto.UserPrivilegesView;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.in.ResolveUserAccessRightsUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.AccessNames;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.AccessMetadataProvider;

@Component
@AllArgsConstructor
public class AccessMetadataAdapter implements AccessMetadataProvider {
  private final ResolveUserAccessRightsUseCase resolveUserAccessRightsUseCase;

  @Override
  public AccessNames getAccessNames(final Set<Long> roleIds, final Set<Long> permissionIds) {
    final UserPrivilegesView view =
        this.resolveUserAccessRightsUseCase.resolvePrivileges(roleIds, permissionIds);
    final Set<String> roles =
        view.roles().stream().map(UserPrivilegesView.RoleView::name).collect(Collectors.toSet());

    final Set<String> permissions =
        view.permissions().stream()
            .map(UserPrivilegesView.PermissionView::name)
            .collect(Collectors.toSet());
    return new AccessNames(roles, permissions);
  }
}

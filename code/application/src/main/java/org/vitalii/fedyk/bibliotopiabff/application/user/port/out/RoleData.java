package org.vitalii.fedyk.bibliotopiabff.application.user.port.out;

import java.util.Set;

/**
 * ACL view — translates a role from the Security domain into the language of the User domain. This
 * record resides in the User domain and is independent of any Security models. If the Security
 * domain changes its models, only the adapter is modified, while this contract remains stable.
 */
public record RoleData(Long roleId, String roleName, Set<String> permissionNames) {}

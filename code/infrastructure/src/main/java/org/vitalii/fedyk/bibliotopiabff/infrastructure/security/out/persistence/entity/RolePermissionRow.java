package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.persistence.entity;

public record RolePermissionRow(
    Long roleId, String roleName, Long permissionId, String permissionName) {}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(
    description =
        "Response body containing the authenticated user's identity, roles, and granular permissions")
public record UserIdentityDto(
    @Schema(description = "Unique database identifier of the authenticated user", example = "1")
        Long id,
    @Schema(
            description = "List of security roles assigned to the user",
            example = "[\"USER\", \"LIBRARIAN\"]")
        List<String> roles,
    @Schema(
            description =
                "List of granular permissions granted to the user for resource access control",
            example = "[\"books:read\", \"books:write\"]")
        List<String> permissions) {}

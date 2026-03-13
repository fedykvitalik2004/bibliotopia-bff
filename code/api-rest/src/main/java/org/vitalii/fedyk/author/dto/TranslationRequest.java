package org.vitalii.fedyk.author.dto;

import static org.vitalii.fedyk.localization.ValidationMessageConstants.DESCRIPTION_MANDATORY;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.FIRST_NAME_MANDATORY;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.FIRST_NAME_SIZE;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.LAST_NAME_MANDATORY;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.LAST_NAME_SIZE;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Details for a specific language translation of the author")
public record TranslationRequest(
    @NotBlank(message = FIRST_NAME_MANDATORY)
        @Size(max = 100, message = FIRST_NAME_SIZE)
        @Schema(
            description = "The author's first name",
            maxLength = 100,
            example = "Vitalii",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String firstName,
    @NotBlank(message = LAST_NAME_MANDATORY)
        @Size(max = 100, message = LAST_NAME_SIZE)
        @Schema(
            description = "The author's last name",
            maxLength = 100,
            example = "Fedyk",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String lastName,
    @NotBlank(message = DESCRIPTION_MANDATORY)
        @Schema(
            description = "The professional biography description",
            example = "Renowned author of technical books.",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String description) {}

package org.vitalii.fedyk.feedback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Represents a system improvement item")
public record ImprovementResponse(
    @Schema(description = "Unique identifier", example = "1") Long id,
    @Schema(description = "Title of the improvement", example = "Refactor API Controllers")
        String title,
    @Schema(
            description = "Detailed description",
            example = "Apply OpenAPI annotations to all v1 controllers.")
        String description,
    @Schema(description = "Type of improvement", example = "REFACTORING") String type,
    @Schema(description = "Importance level (1-10)", example = "8") Integer importance) {}

package org.vitalii.fedyk.author.dto;

import static org.vitalii.fedyk.localization.ValidationMessageConstants.BIRTH_DATE_PAST;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.BIRTH_DATE_REQUIRED;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.TRANSLATIONS_REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import org.vitalii.fedyk.annotation.validation.ValidDateRange;

@ValidDateRange
@Builder
@Schema(description = "Request object for creating a new author")
public record AuthorRequest(
    @NotNull(message = BIRTH_DATE_REQUIRED)
        @Past(message = BIRTH_DATE_PAST)
        @Schema(
            description = "Date of birth",
            example = "1990-01-01",
            requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate birthDate,
    @Schema(
            description = "Date of death, if applicable",
            example = "2025-05-10",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDate deathDate,
    @NotEmpty(message = TRANSLATIONS_REQUIRED)
        @Schema(
            description = "List of author translations/biographies in different languages",
            requiredMode = Schema.RequiredMode.REQUIRED)
        List<@Valid TranslationRequest> translations) {}

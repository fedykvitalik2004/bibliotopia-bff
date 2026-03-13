package org.vitalii.fedyk.book.dto;

import static org.vitalii.fedyk.localization.ValidationMessageConstants.AUTHOR_IDS_REQUIRED;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.AUTHOR_ID_INVALID;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.DESCRIPTION_MANDATORY;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.GENRES_REQUIRED;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.GENRE_INVALID;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.ISBN_MANDATORY;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.PRICE_MANDATORY;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.PRICE_POSITIVE;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.TITLE_MANDATORY;
import static org.vitalii.fedyk.localization.ValidationMessageConstants.TRANSLATIONS_REQUIRED;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Request object for creating a new book")
public record BookRequest(
    @NotBlank(message = ISBN_MANDATORY)
        @Schema(
            description = "The international standard book number",
            example = "978-3-16-148410-0",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String isbn,
    @NotNull(message = PRICE_MANDATORY)
        @Positive(message = PRICE_POSITIVE)
        @Schema(
            description = "The price of the book",
            example = "29.99",
            requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal price,
    @NotEmpty(message = TRANSLATIONS_REQUIRED)
        @ArraySchema(
            schema =
                @Schema(
                    description = "List of translations including title and description",
                    requiredMode = Schema.RequiredMode.REQUIRED))
        List<@Valid TranslationRequest> translations,
    @NotEmpty(message = GENRES_REQUIRED)
        @ArraySchema(
            schema =
                @Schema(
                    description = "List of book genres",
                    example = "[\"Fiction\", \"Science\"]",
                    requiredMode = Schema.RequiredMode.REQUIRED))
        List<@NotBlank(message = GENRE_INVALID) String> genres,
    @NotEmpty(message = AUTHOR_IDS_REQUIRED)
        @ArraySchema(
            schema =
                @Schema(
                    description = "List of associated author IDs",
                    example = "[1, 2]",
                    requiredMode = Schema.RequiredMode.REQUIRED))
        List<@Positive(message = AUTHOR_ID_INVALID) Long> authorIds) {

  @Schema(description = "Details for a specific language translation of the book")
  public record TranslationRequest(
      @NotBlank(message = TITLE_MANDATORY)
          @Schema(
              description = "The translated title of the book",
              example = "The Great Gatsby",
              requiredMode = Schema.RequiredMode.REQUIRED)
          String title,
      @NotBlank(message = DESCRIPTION_MANDATORY)
          @Schema(
              description = "The translated description of the book",
              example = "A novel set in the Jazz Age.",
              requiredMode = Schema.RequiredMode.REQUIRED)
          String description) {}
}

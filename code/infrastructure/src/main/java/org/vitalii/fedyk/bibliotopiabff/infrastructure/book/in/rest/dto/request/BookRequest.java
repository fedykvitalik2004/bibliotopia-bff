package org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.dto.request;

import static org.vitalii.fedyk.bibliotopiabff.domain.common.ValidationMessageConstants.LANGUAGE_MANDATORY;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import org.vitalii.fedyk.bibliotopiabff.domain.common.ValidationMessageConstants;

@Schema(description = "Request object for creating a new book")
public record BookRequest(
    @NotBlank(message = ValidationMessageConstants.ISBN_MANDATORY)
        @Schema(
            description = "The international standard book number",
            example = "978-3-16-148410-0",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String isbn,
    @NotNull(message = ValidationMessageConstants.PRICE_MANDATORY)
        @Positive(message = ValidationMessageConstants.PRICE_POSITIVE)
        @Schema(
            description = "The price of the book",
            example = "29.99",
            requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal price,
    @NotEmpty(message = ValidationMessageConstants.TRANSLATIONS_REQUIRED)
        @ArraySchema(
            schema =
                @Schema(
                    description = "List of translations including title and description",
                    requiredMode = Schema.RequiredMode.REQUIRED))
        List<@Valid TranslationRequest> translations,
    @NotEmpty(message = ValidationMessageConstants.GENRES_REQUIRED)
        @ArraySchema(
            schema =
                @Schema(
                    description = "List of book genres",
                    example = "[\"Fiction\", \"Science\"]",
                    requiredMode = Schema.RequiredMode.REQUIRED))
        List<@NotBlank(message = ValidationMessageConstants.GENRE_INVALID) String> genres,
    @NotEmpty(message = ValidationMessageConstants.AUTHOR_IDS_REQUIRED)
        @ArraySchema(
            schema =
                @Schema(
                    description = "List of associated author IDs",
                    example = "[1, 2]",
                    requiredMode = Schema.RequiredMode.REQUIRED))
        List<@Positive(message = ValidationMessageConstants.AUTHOR_ID_INVALID) Long> authorIds) {

  @Schema(description = "Details for a specific language translation of the book")
  public record TranslationRequest(
      @NotBlank(message = ValidationMessageConstants.TITLE_MANDATORY)
          @Schema(
              description = "The translated title of the book",
              example = "The Great Gatsby",
              requiredMode = Schema.RequiredMode.REQUIRED)
          String title,
      @NotBlank(message = ValidationMessageConstants.DESCRIPTION_MANDATORY)
          @Schema(
              description = "The translated description of the book",
              example = "A novel set in the Jazz Age.",
              requiredMode = Schema.RequiredMode.REQUIRED)
          String description,
      @NotBlank(message = LANGUAGE_MANDATORY)
          @Size(min = 2, max = 5)
          @Schema(
              description = "ISO language code (e.g., 'en', 'uk')",
              minLength = 2,
              maxLength = 5,
              example = "uk",
              requiredMode = Schema.RequiredMode.REQUIRED)
          String language) {}
}

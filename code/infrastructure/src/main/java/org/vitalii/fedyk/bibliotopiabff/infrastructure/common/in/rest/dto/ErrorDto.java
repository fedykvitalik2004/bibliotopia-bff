package org.vitalii.fedyk.bibliotopiabff.infrastructure.common.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
    name = "ErrorDto",
    description = "Represents a standard error response returned by the API.")
public class ErrorDto {

  @Schema(
      description = "Title which shortly describes the problem.",
      example = "Invalid request data",
      required = true)
  private String title;

  @Schema(
      description =
          "Additional details or parameters related to the error. " + "May include context.",
      example = "Field 'name' must not be empty")
  private Object additionalParams;
}

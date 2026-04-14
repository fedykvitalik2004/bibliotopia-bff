package org.vitalii.fedyk.bibliotopiabff.infrastructure.feedback.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vitalii.fedyk.bibliotopiabff.application.feedback.port.in.GetImprovementsUseCase;
import org.vitalii.fedyk.bibliotopiabff.domain.feedback.model.ImprovementItem;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.feedback.in.rest.dto.ImprovementResponse;

@RestController
@RequestMapping("/api/v1/improvements")
@RequiredArgsConstructor
@Tag(name = "Improvement", description = "Endpoints for retrieving system improvements")
public class ImprovementController {
  private final GetImprovementsUseCase getImprovementsUseCase;

  @GetMapping
  @Operation(
      summary = "Get all improvements",
      description = "Retrieves a list of all recorded system improvements")
  @ApiResponse(
      responseCode = "200",
      description = "List of improvements retrieved successfully",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ImprovementResponse.class))))
  public ResponseEntity<List<ImprovementResponse>> getAll() {
    return ResponseEntity.ok(
        this.getImprovementsUseCase.execute().stream().map(this::toResponse).toList());
  }

  private ImprovementResponse toResponse(final ImprovementItem item) {
    return new ImprovementResponse(
        item.id(), item.title(), item.description(), item.type(), item.importance());
  }
}

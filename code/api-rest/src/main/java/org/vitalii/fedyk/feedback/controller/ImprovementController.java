package org.vitalii.fedyk.feedback.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vitalii.fedyk.feedback.dto.ImprovementResponse;
import org.vitalii.fedyk.feedback.model.ImprovementItem;
import org.vitalii.fedyk.feedback.usecase.GetImprovementsUseCase;

@RestController
@AllArgsConstructor
public class ImprovementController {
  private GetImprovementsUseCase getImprovementsUseCase;

  @GetMapping
  public ResponseEntity<List<ImprovementResponse>> getAll() {
    return ResponseEntity.ok(
        this.getImprovementsUseCase.execute().stream().map(this::toResponse).toList());
  }

  private ImprovementResponse toResponse(final ImprovementItem item) {
    return new ImprovementResponse(
        item.id(), item.title(), item.description(), item.type(), item.importance());
  }
}

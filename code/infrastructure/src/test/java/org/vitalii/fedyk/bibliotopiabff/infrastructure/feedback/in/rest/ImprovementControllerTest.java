package org.vitalii.fedyk.bibliotopiabff.infrastructure.feedback.in.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.vitalii.fedyk.bibliotopiabff.application.feedback.port.in.GetImprovementsUseCase;
import org.vitalii.fedyk.bibliotopiabff.domain.feedback.model.ImprovementItem;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.feedback.in.rest.dto.ImprovementResponse;

@ExtendWith(MockitoExtension.class)
class ImprovementControllerTest {
  @Mock private GetImprovementsUseCase getImprovementsUseCase;

  @InjectMocks private ImprovementController controller;

  @Test
  void shouldReturnAllProductImprovements() {
    // Given
    final var createdAt = OffsetDateTime.of(2026, 12, 22, 13, 56, 24, 24, ZoneOffset.ofHours(2));
    final var item1 = new ImprovementItem(1L, "Title1", "Desc1", "TYPE1", 1, createdAt);

    final var item2 = new ImprovementItem(2L, "Title2", "Desc2", "TYPE2", 2, createdAt);

    final var expected =
        List.of(
            ImprovementResponse.builder()
                .id(1L)
                .title("Title1")
                .description("Desc1")
                .type("TYPE1")
                .importance(1)
                .build(),
            ImprovementResponse.builder()
                .id(2L)
                .title("Title2")
                .description("Desc2")
                .type("TYPE2")
                .importance(2)
                .build());

    when(this.getImprovementsUseCase.execute()).thenReturn(List.of(item1, item2));

    // When
    final ResponseEntity<List<ImprovementResponse>> response = this.controller.getAll();

    // Then
    assertEquals(200, response.getStatusCode().value());
    assertEquals(expected, response.getBody());

    verify(this.getImprovementsUseCase).execute();
  }
}

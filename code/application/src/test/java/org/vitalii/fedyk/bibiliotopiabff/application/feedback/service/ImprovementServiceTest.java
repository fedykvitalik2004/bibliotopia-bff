package org.vitalii.fedyk.bibiliotopiabff.application.feedback.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vitalii.fedyk.bibliotopiabff.application.feedback.port.out.ImprovementQueryPort;
import org.vitalii.fedyk.bibliotopiabff.application.feedback.service.ImprovementService;
import org.vitalii.fedyk.bibliotopiabff.domain.feedback.model.ImprovementItem;

@ExtendWith(MockitoExtension.class)
class ImprovementServiceTest {

  @Mock private ImprovementQueryPort improvementQueryPort;

  @InjectMocks private ImprovementService improvementService;

  @Test
  void execute_ShouldReturnAllImprovementsFromPort() {
    // Given
    final var createdAt = OffsetDateTime.now();
    final var mockData =
        List.of(new ImprovementItem(1L, "Title", "Description", "BUG", 2, createdAt));

    when(this.improvementQueryPort.getAllImprovements()).thenReturn(mockData);

    // When
    final var result = this.improvementService.execute();

    // Then
    assertThat(result).hasSize(1).isEqualTo(mockData);

    verify(this.improvementQueryPort).getAllImprovements();
  }
}

package org.vitalii.fedyk.infrastructure.feedback.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.vitalii.fedyk.bibliotopiabff.domain.feedback.model.ImprovementItem;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.feedback.out.persistence.ImprovementPersistenceAdapter;

@SpringBootTest(classes = ImprovementPersistenceAdapter.class)
@AutoConfigureJdbc
@ActiveProfiles("test")
class ImprovementPersistenceAdapterIT {
  @Autowired private ImprovementPersistenceAdapter improvementPersistenceAdapter;

  @Test
  void shouldReturnAllImprovementsSortedByDateDescending() {
    // Given
    final var expected =
        List.of(
            ImprovementItem.builder()
                .id(5L)
                .title("Bug #5")
                .description("Description 5")
                .type("BUG")
                .importance(1)
                .createdAt(OffsetDateTime.parse("2026-03-30T15:11Z"))
                .build(),
            ImprovementItem.builder()
                .id(4L)
                .title("Bug #4")
                .description("Description 4")
                .type("BUG")
                .importance(1)
                .createdAt(OffsetDateTime.parse("2026-03-29T15:11Z"))
                .build(),
            ImprovementItem.builder()
                .id(3L)
                .title("Feature #3")
                .description("Auto generated description 3")
                .type("FEATURE")
                .importance(2)
                .createdAt(OffsetDateTime.parse("2026-03-29T11:11Z"))
                .build(),
            ImprovementItem.builder()
                .id(3L)
                .title("Bug #3")
                .description("Description 3")
                .type("BUG")
                .importance(6)
                .createdAt(OffsetDateTime.parse("2026-03-28T15:11Z"))
                .build(),
            ImprovementItem.builder()
                .id(2L)
                .title("Feature #2")
                .description("Auto generated description 2")
                .type("FEATURE")
                .importance(7)
                .createdAt(OffsetDateTime.parse("2026-03-28T11:11Z"))
                .build(),
            ImprovementItem.builder()
                .id(2L)
                .title("Bug #2")
                .description("Description 2")
                .type("BUG")
                .importance(3)
                .createdAt(OffsetDateTime.parse("2026-03-27T15:11Z"))
                .build(),
            ImprovementItem.builder()
                .id(1L)
                .title("Feature #1")
                .description("Auto generated description 1")
                .type("FEATURE")
                .importance(7)
                .createdAt(OffsetDateTime.parse("2026-03-27T11:11Z"))
                .build(),
            ImprovementItem.builder()
                .id(1L)
                .title("Bug #1")
                .description("Description 1")
                .type("BUG")
                .importance(5)
                .createdAt(OffsetDateTime.parse("2026-03-26T15:11Z"))
                .build());

    // When
    final var actual = this.improvementPersistenceAdapter.getAllImprovements();

    // Then
    assertEquals(expected, actual);
  }
}

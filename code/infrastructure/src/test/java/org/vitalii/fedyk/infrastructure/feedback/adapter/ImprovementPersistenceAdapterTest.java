package org.vitalii.fedyk.infrastructure.feedback.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.vitalii.fedyk.feedback.model.ImprovementItem;

@ExtendWith(MockitoExtension.class)
class ImprovementPersistenceAdapterTest {
  @Mock private JdbcTemplate jdbcTemplate;

  @InjectMocks private ImprovementPersistenceAdapter adapter;

  @Captor private ArgumentCaptor<RowMapper<ImprovementItem>> rowMapperCaptor;

  @SneakyThrows
  @Test
  void getAllImprovements_ShouldMapResultSetCorrectly() {
    // Given
    final var rs = mock(ResultSet.class);

    final var item =
        ImprovementItem.builder()
            .id(1L)
            .title("Sample Title")
            .description("Sample Desc")
            .type("BUG")
            .importance(1)
            .build();

    when(rs.getLong("id")).thenReturn(1L);
    when(rs.getString("title")).thenReturn("Sample Title");
    when(rs.getString("description")).thenReturn("Sample Desc");
    when(rs.getString("type")).thenReturn("BUG");
    when(rs.getInt("importance")).thenReturn(1);

    when(this.jdbcTemplate.query(anyString(), this.rowMapperCaptor.capture()))
        .thenReturn(List.of(item));

    // When
    final var results = adapter.getAllImprovements();

    // Then
    assertThat(results).isEqualTo(List.of(item));

    final var capturedMapper = this.rowMapperCaptor.getValue();
    final var mappedItem = capturedMapper.mapRow(rs, 0);
    assertThat(mappedItem).isEqualTo(item);
  }
}

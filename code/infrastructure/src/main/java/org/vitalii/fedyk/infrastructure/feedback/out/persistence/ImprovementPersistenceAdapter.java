package org.vitalii.fedyk.infrastructure.feedback.out.persistence;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.application.feedback.port.out.ImprovementQueryPort;
import org.vitalii.fedyk.domain.feedback.model.ImprovementItem;

@Repository
@AllArgsConstructor
public class ImprovementPersistenceAdapter implements ImprovementQueryPort {
  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<ImprovementItem> getAllImprovements() {
    final String sql =
        """
                    SELECT id, title, description, 'BUG' AS type, importance, created_at FROM bug_reports
                    UNION
                    SELECT id, title, description, 'FEATURE' AS type, importance, created_at FROM feature_requests
                    ORDER BY created_at DESC
                    """;

    return jdbcTemplate.query(
        sql,
        (rs, rowNum) ->
            ImprovementItem.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .type(rs.getString("type"))
                .importance(rs.getInt("importance"))
                .createdAt(rs.getObject("created_at", OffsetDateTime.class))
                .build());
  }
}

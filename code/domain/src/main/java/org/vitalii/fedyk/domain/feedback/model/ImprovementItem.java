package org.vitalii.fedyk.domain.feedback.model;

import java.time.OffsetDateTime;
import lombok.Builder;

@Builder
public record ImprovementItem(
    Long id,
    String title,
    String description,
    String type,
    Integer importance,
    OffsetDateTime createdAt) {}

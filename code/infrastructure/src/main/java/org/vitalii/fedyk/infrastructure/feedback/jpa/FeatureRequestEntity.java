package org.vitalii.fedyk.infrastructure.feedback.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "feature_requests")
public class FeatureRequestEntity extends BaseFeedback {
  private Integer votesCount;
}

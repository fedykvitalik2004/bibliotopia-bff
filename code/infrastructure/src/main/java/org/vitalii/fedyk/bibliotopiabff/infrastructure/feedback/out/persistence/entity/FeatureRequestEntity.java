package org.vitalii.fedyk.bibliotopiabff.infrastructure.feedback.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "feature_requests")
public class FeatureRequestEntity extends BaseFeedback {
  private Integer votesCount;
}

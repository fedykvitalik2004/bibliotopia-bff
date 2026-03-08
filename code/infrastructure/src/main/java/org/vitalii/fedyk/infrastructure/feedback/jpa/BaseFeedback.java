package org.vitalii.fedyk.infrastructure.feedback.jpa;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public abstract class BaseFeedback {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  protected String title;

  protected String description;

  protected String importance;

  protected OffsetDateTime createdAt;
}

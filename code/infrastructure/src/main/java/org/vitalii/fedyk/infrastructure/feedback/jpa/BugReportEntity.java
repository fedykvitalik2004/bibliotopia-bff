package org.vitalii.fedyk.infrastructure.feedback.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bug_reports")
public class BugReportEntity extends BaseFeedback {
  private String stackTrace;
}

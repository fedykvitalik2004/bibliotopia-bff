package org.vitalii.fedyk.bibliotopiabff.infrastructure.feedback.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bug_reports")
public class BugReportEntity extends BaseFeedback {
  private String stackTrace;
}

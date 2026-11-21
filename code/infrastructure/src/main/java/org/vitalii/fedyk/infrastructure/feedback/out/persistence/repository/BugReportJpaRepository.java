package org.vitalii.fedyk.infrastructure.feedback.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.infrastructure.feedback.out.persistence.entity.BugReportEntity;

@Repository
public interface BugReportJpaRepository extends JpaRepository<BugReportEntity, Long> {}

package org.vitalii.fedyk.infrastructure.feedback.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRequestJpaRepository extends JpaRepository<FeatureRequestEntity, Long> {}

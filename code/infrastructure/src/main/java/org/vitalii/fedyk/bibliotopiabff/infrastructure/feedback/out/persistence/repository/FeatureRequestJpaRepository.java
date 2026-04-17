package org.vitalii.fedyk.bibliotopiabff.infrastructure.feedback.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.feedback.out.persistence.entity.FeatureRequestEntity;

@Repository
public interface FeatureRequestJpaRepository extends JpaRepository<FeatureRequestEntity, Long> {}

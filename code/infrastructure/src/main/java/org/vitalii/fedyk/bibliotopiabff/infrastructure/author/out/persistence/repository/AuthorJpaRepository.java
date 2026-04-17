package org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.entity.AuthorEntity;

public interface AuthorJpaRepository extends JpaRepository<AuthorEntity, Long> {
  int countByIdIn(Collection<Long> ids);
}

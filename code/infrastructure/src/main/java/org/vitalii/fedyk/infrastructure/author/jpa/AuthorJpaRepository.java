package org.vitalii.fedyk.infrastructure.author.jpa;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpaRepository extends JpaRepository<AuthorEntity, Long> {
  int countByIdIn(Collection<Long> ids);
}

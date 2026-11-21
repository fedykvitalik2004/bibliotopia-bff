package org.vitalii.fedyk.application.author.port.out;

import java.util.List;
import java.util.Optional;
import org.vitalii.fedyk.domain.author.model.Author;

public interface AuthorRepositoryPort {
  Author save(Author author);

  Optional<Author> findById(Long id);

  List<Author> findAll();

  List<Author> findAllByIds(List<Long> authorIds);

  int countByIds(List<Long> ids);
}

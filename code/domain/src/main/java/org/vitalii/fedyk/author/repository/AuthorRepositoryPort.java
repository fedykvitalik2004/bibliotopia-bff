package org.vitalii.fedyk.author.repository;

import java.util.List;
import java.util.Optional;
import org.vitalii.fedyk.author.model.Author;

public interface AuthorRepositoryPort {
  Author save(Author author);

  Optional<Author> findById(Long id);

  List<Author> findAll();

  List<Author> findAllByIds(List<Long> authorIds);

  int countByIds(List<Long> ids);
}

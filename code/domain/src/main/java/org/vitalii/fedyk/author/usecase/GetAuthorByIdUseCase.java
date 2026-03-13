package org.vitalii.fedyk.author.usecase;

import java.util.Optional;
import org.vitalii.fedyk.author.model.Author;

public interface GetAuthorByIdUseCase {
  Optional<Author> getAuthor(final Long id);
}

package org.vitalii.fedyk.application.author.port.in;

import java.util.Optional;
import org.vitalii.fedyk.domain.author.model.Author;

public interface GetAuthorByIdUseCase {
  Optional<Author> getAuthor(final Long id);
}

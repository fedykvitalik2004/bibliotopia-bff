package org.vitalii.fedyk.bibliotopiabff.application.author.port.in;

import java.util.Optional;
import org.vitalii.fedyk.bibliotopiabff.domain.author.model.Author;

public interface GetAuthorByIdUseCase {
  Optional<Author> getAuthor(final Long id);
}

package org.vitalii.fedyk.application.author.port.in;

import org.vitalii.fedyk.domain.author.model.Author;

public interface CreateAuthorUseCase {
  Author createAuthor(Author author);
}

package org.vitalii.fedyk.bibliotopiabff.application.author.port.in;

import org.vitalii.fedyk.bibliotopiabff.domain.author.model.Author;

public interface CreateAuthorUseCase {
  Author createAuthor(Author author);
}

package org.vitalii.fedyk.application.author.port.in;

import java.util.List;
import org.vitalii.fedyk.domain.author.model.Author;

public interface GetAllAuthorsUseCase {
  List<Author> getAllAuthors();
}

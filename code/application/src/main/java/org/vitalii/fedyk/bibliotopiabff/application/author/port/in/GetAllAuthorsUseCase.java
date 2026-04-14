package org.vitalii.fedyk.bibliotopiabff.application.author.port.in;

import java.util.List;
import org.vitalii.fedyk.bibliotopiabff.domain.author.model.Author;

public interface GetAllAuthorsUseCase {
  List<Author> getAllAuthors();
}

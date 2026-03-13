package org.vitalii.fedyk.author.usecase;

import java.util.List;
import org.vitalii.fedyk.author.model.Author;

public interface GetAllAuthorsUseCase {
  List<Author> getAllAuthors();
}

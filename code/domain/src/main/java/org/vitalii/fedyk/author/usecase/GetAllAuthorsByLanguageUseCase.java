package org.vitalii.fedyk.author.usecase;

import java.util.List;
import org.vitalii.fedyk.author.Author;

public interface GetAllAuthorsByLanguageUseCase {
  List<Author> getAllAuthors(String language);
}

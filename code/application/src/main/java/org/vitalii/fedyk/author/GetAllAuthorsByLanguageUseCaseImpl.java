package org.vitalii.fedyk.author;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.author.repository.AuthorRepository;
import org.vitalii.fedyk.author.usecase.GetAuthorUseCase;
import org.vitalii.fedyk.common.enumeration.Language;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetAuthorUseCaseImpl implements GetAuthorUseCase {
  private final AuthorRepository authorRepository;

  @Override
  public Author getAuthor(String language, UUID id) {
    final Language languageEnum = Language.fromValue(language);
    return authorRepository.findById(languageEnum, id)
            .orElseThrow();
  }
}

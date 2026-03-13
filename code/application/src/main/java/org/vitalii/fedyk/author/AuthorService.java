package org.vitalii.fedyk.author;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.author.model.Author;
import org.vitalii.fedyk.author.repository.AuthorRepositoryPort;
import org.vitalii.fedyk.author.usecase.CreateAuthorUseCase;
import org.vitalii.fedyk.author.usecase.GetAllAuthorsUseCase;
import org.vitalii.fedyk.author.usecase.GetAuthorByIdUseCase;

@Service
@AllArgsConstructor
public class AuthorService
    implements CreateAuthorUseCase, GetAllAuthorsUseCase, GetAuthorByIdUseCase {
  private final AuthorRepositoryPort authorRepositoryPort;

  @Override
  public Author createAuthor(final Author author) {
    return this.authorRepositoryPort.save(author);
  }

  @Override
  public List<Author> getAllAuthors() {
    return this.authorRepositoryPort.findAll();
  }

  @Override
  public Optional<Author> getAuthor(final Long id) {
    return this.authorRepositoryPort.findById(id);
  }
}

package org.vitalii.fedyk.bibliotopiabff.application.author.service;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.bibliotopiabff.application.author.port.in.CreateAuthorUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.author.port.in.GetAllAuthorsUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.author.port.in.GetAuthorByIdUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.author.port.out.AuthorRepositoryPort;
import org.vitalii.fedyk.bibliotopiabff.domain.author.model.Author;

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

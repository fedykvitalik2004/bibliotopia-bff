package org.vitalii.fedyk.bibiliotopiabff.application.author.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vitalii.fedyk.bibliotopiabff.application.author.port.out.AuthorRepositoryPort;
import org.vitalii.fedyk.bibliotopiabff.application.author.service.AuthorService;
import org.vitalii.fedyk.bibliotopiabff.domain.author.model.Author;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

  @Mock private AuthorRepositoryPort authorRepositoryPort;

  @InjectMocks private AuthorService authorService;

  @Test
  void shouldCreateAuthorSuccessfully() {
    // Given
    final Author authorToCreate =
        Author.builder()
            .birthDate(LocalDate.of(1990, 1, 1))
            .deathDate(LocalDate.of(2020, 1, 1))
            .build();
    final Author expectedAuthor =
        Author.builder()
            .id(1L)
            .birthDate(LocalDate.of(1990, 1, 1))
            .deathDate(LocalDate.of(2020, 1, 1))
            .build();

    when(this.authorRepositoryPort.save(authorToCreate)).thenReturn(expectedAuthor);

    // When
    Author actualAuthor = this.authorService.createAuthor(authorToCreate);

    // Then
    assertEquals(expectedAuthor, actualAuthor);
  }

  @Test
  void shouldReturnAllAuthors() {
    // Given
    final List<Author> expectedAuthors =
        List.of(
            Author.builder()
                .id(1L)
                .birthDate(LocalDate.of(1990, 1, 1))
                .deathDate(LocalDate.of(2020, 1, 1))
                .build());
    when(this.authorRepositoryPort.findAll()).thenReturn(expectedAuthors);

    // When
    List<Author> actualAuthors = this.authorService.getAllAuthors();

    // Then
    assertEquals(expectedAuthors, actualAuthors);
  }

  @Test
  void shouldReturnEmptyListWhenNoAuthorsExist() {
    // Given
    when(this.authorRepositoryPort.findAll()).thenReturn(Collections.emptyList());

    // When
    List<Author> actualAuthors = this.authorService.getAllAuthors();

    // THen
    assertTrue(actualAuthors.isEmpty());
  }

  @Test
  void shouldReturnAuthorWhenFound() {
    // Given
    Author expectedAuthor =
        Author.builder()
            .id(1L)
            .birthDate(LocalDate.of(1990, 1, 1))
            .deathDate(LocalDate.of(2020, 1, 1))
            .build();
    when(this.authorRepositoryPort.findById(1L)).thenReturn(Optional.of(expectedAuthor));

    // When
    Optional<Author> actualAuthor = this.authorService.getAuthor(1L);

    // Then
    assertTrue(actualAuthor.isPresent());
    assertEquals(expectedAuthor, actualAuthor.get());
  }

  @Test
  void shouldReturnEmptyOptionalWhenAuthorNotFound() {
    // Given
    when(this.authorRepositoryPort.findById(1L)).thenReturn(Optional.empty());

    // When
    Optional<Author> actualAuthor = this.authorService.getAuthor(1L);

    // Then
    assertTrue(actualAuthor.isEmpty());
  }
}

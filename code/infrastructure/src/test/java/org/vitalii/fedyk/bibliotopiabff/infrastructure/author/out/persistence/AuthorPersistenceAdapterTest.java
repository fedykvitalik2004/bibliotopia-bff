package org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vitalii.fedyk.bibliotopiabff.domain.author.model.Author;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.entity.AuthorEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.mapper.AuthorEntityMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.repository.AuthorJpaRepository;

@ExtendWith(MockitoExtension.class)
class AuthorPersistenceAdapterTest {

  @Mock private AuthorEntityMapper mapper;

  @Mock private AuthorJpaRepository repository;

  @InjectMocks private AuthorPersistenceAdapter adapter;

  @Test
  void shouldSaveAuthorSuccessfully() {
    // Given
    final var author = Instancio.create(Author.class);
    final var entity = Instancio.create(AuthorEntity.class);

    when(this.mapper.toAuthorEntity(author)).thenReturn(entity);
    when(this.repository.save(entity)).thenReturn(entity);
    when(this.mapper.toAuthor(entity)).thenReturn(author);

    // When
    final var result = adapter.save(author);

    // Then
    assertThat(result).isEqualTo(author);

    verify(this.mapper).toAuthorEntity(author);
    verify(this.repository).save(entity);
    verify(this.mapper).toAuthor(entity);
  }

  @Test
  void shouldFindByIdWhenExists() {
    // Given
    final var id = 1L;
    final var entity = Instancio.create(AuthorEntity.class);
    final var author = Instancio.create(Author.class);

    when(this.repository.findById(id)).thenReturn(Optional.of(entity));
    when(this.mapper.toAuthor(entity)).thenReturn(author);

    // When
    final var result = adapter.findById(id);

    // Then
    assertThat(result).isPresent().contains(author);
    verify(this.repository).findById(id);
  }

  @Test
  void shouldReturnEmptyWhenAuthorNotFound() {
    // Given
    when(this.repository.findById(1L)).thenReturn(Optional.empty());

    // When
    final var result = adapter.findById(1L);

    // Then
    assertThat(result).isEmpty();
  }

  @Test
  void shouldFindAllAuthors() {
    // Given
    final var entities = Instancio.stream(AuthorEntity.class).limit(2).toList();
    Author author1 = Instancio.create(Author.class);
    Author author2 = Instancio.create(Author.class);

    when(this.repository.findAll()).thenReturn(entities);
    when(this.mapper.toAuthor(entities.get(0))).thenReturn(author1);
    when(this.mapper.toAuthor(entities.get(1))).thenReturn(author2);

    // When
    final var result = adapter.findAll();

    // Then
    assertThat(result).hasSize(2).containsExactly(author1, author2);
    verify(this.repository).findAll();
  }
}

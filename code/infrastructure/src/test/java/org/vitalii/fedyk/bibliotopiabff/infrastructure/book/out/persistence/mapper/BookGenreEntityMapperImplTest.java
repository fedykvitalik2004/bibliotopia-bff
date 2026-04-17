package org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.BookGenre;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.entity.BookGenreEntity;

class BookGenreEntityMapperImplTest {
  private final BookGenreEntityMapper mapper = new BookGenreEntityMapperImpl();

  @Test
  void shouldMapDomainToEntity() {
    // Given
    final var domain = Instancio.create(BookGenre.class);

    // When
    final var actual = mapper.toBookGenreEntity(domain);

    // Then
    assertNotNull(actual);
    assertEquals(domain.genre(), actual.getGenre());
  }

  @Test
  void shouldMapEntityToDomain() {
    // Given
    final var entity = BookGenreEntity.builder().genre("Fantasy").build();

    // When
    final var actual = this.mapper.toBookGenre(entity);

    // Then
    assertNotNull(actual);
    assertEquals("Fantasy", actual.genre());
  }
}

package org.vitalii.fedyk.infrastructure.book.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.book.model.BookTranslation;
import org.vitalii.fedyk.infrastructure.book.jpa.BookTranslationEntity;

class BookTranslationEntityMapperImplTest {
  private final BookTranslationEntityMapper mapper = new BookTranslationEntityMapperImpl();

  @Test
  void shouldMapDomainToEntity() {
    // Given
    final BookTranslation domain = Instancio.create(BookTranslation.class);

    // when
    final var entity = this.mapper.toBookTranslationEntity(domain);

    // then
    assertNotNull(entity);
    assertEquals(domain.title(), entity.getTitle());
    assertEquals(domain.description(), entity.getDescription());
  }

  @Test
  void shouldMapEntityToDomain() {
    // Given
    final var entity =
        BookTranslationEntity.builder()
            .title("Domain Driven Design")
            .description("Strategic design in DDD")
            .build();

    // When
    final var domain = mapper.toBookTranslation(entity);

    // Then
    assertNotNull(domain);
    assertEquals("Domain Driven Design", domain.title());
    assertEquals("Strategic design in DDD", domain.description());
  }
}

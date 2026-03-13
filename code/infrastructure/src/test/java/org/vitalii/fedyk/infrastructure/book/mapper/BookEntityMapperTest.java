package org.vitalii.fedyk.infrastructure.book.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.vitalii.fedyk.book.model.Book;
import org.vitalii.fedyk.book.model.BookGenre;
import org.vitalii.fedyk.infrastructure.author.jpa.AuthorEntity;
import org.vitalii.fedyk.infrastructure.book.jpa.BookEntity;

@SpringJUnitConfig
@Import({
  BookEntityMapperImpl.class,
  BookTranslationEntityMapperImpl.class,
  BookGenreEntityMapperImpl.class
})
class BookEntityMapperTest {
  @Autowired private BookEntityMapper bookEntityMapper;

  @Test
  void shouldMapBookDomainToBookEntityCorrectly() {
    // Given
    final var genre = new BookGenre("Fiction");

    final Book book = Instancio.of(Book.class).set(field("genres"), List.of(genre)).create();
    final List<AuthorEntity> authorEntityList = Instancio.createList(AuthorEntity.class);

    final var expected =
        BookEntity.builder()
            .id(book.getId())
            .isbn(book.getIsbn())
            .pageCount(book.getPageCount())
            .coverImageUrl(book.getCoverImageUrl())
            .price(book.getPrice())
            .build();

    // When
    final var actual = this.bookEntityMapper.toBookEntity(book, authorEntityList);

    // Then
    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields("authors", "genres", "translations")
        .isEqualTo(expected);

    final var genreActual = actual.getGenres().get(0);
    assertEquals("Fiction", genreActual.getGenre());
    assertNotNull(genreActual.getBook());
  }
}

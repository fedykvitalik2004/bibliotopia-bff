package org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.bibliotopiabff.application.book.dto.BookPricingResult;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.BookGenre;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.BookTranslation;

class BookWebResponseMapperTest {
  private BookWebResponseMapperImpl mapper;

  @BeforeEach
  void setUp() {
    mapper = new BookWebResponseMapperImpl();
  }

  @Test
  void shouldMapBookPricingResultToBookResponse() {
    // Given
    final var bookId = 1L;
    final var isbn = "978-3-16-148410-0";
    final var pages = 350;
    final var coverUrl = "http://example.com/cover.jpg";
    final var basePrice = new BigDecimal("100.00");
    final var finalPrice = new BigDecimal("85.00");

    final var book = mock(Book.class);
    when(book.getId()).thenReturn(bookId);
    when(book.getIsbn()).thenReturn(isbn);
    when(book.getPageCount()).thenReturn(pages);
    when(book.getCoverImageUrl()).thenReturn(coverUrl);
    when(book.getPrice()).thenReturn(basePrice);

    final var translation = new BookTranslation("Title", "Description", "ua");

    when(book.getTranslations()).thenReturn(List.of(translation));
    when(book.getGenres()).thenReturn(List.of(new BookGenre("FANTASY")));
    when(book.getAuthorIds()).thenReturn(List.of(10L, 11L));

    final var pricingResult = new BookPricingResult(book, finalPrice);

    // When
    final var response = mapper.toBookResponse(pricingResult);

    // Then
    assertNotNull(response);
    assertEquals(bookId, response.id());
    assertEquals(isbn, response.isbn());
    assertEquals(pages, response.pageCount());
    assertEquals(coverUrl, response.coverImageUrl());
    assertEquals(basePrice, response.price());
    assertEquals(finalPrice, response.finalPrice());
    assertEquals(1, response.translations().size());
    assertEquals("Title", response.translations().get(0).title());
    assertEquals("FANTASY", response.genres().get(0)); // Залежить від вашої реалізації mapGenre
    assertEquals(2, response.authorIds().size());
    assertEquals(10L, response.authorIds().get(0));
  }
}

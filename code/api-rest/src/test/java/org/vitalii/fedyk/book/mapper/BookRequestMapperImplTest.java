package org.vitalii.fedyk.book.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.book.dto.BookRequest;
import org.vitalii.fedyk.book.model.CreateBookCommand;

class BookRequestMapperImplTest {
  private final BookRequestMapper mapper = new BookRequestMapperImpl();

  @Test
  void shouldMapBookRequestToCommand() {
    // Given
    final var translationReq = new BookRequest.TranslationRequest("Title", "Desc");
    final var request =
        new BookRequest(
            "978-3-16-148410-0",
            new BigDecimal("29.99"),
            List.of(translationReq),
            List.of("Fantasy"),
            List.of(1L, 2L));

    final var expected =
        CreateBookCommand.builder()
            .isbn("978-3-16-148410-0")
            .price(new BigDecimal("29.99"))
            .translations(
                List.of(
                    CreateBookCommand.TranslationCommand.builder()
                        .title("Title")
                        .description("Desc")
                        .build()))
            .genres(List.of("Fantasy"))
            .authorIds(List.of(1L, 2L))
            .build();
    // When
    final var actual = this.mapper.toCreateBookCommand(request);

    // Then
    assertEquals(expected, actual);
  }
}

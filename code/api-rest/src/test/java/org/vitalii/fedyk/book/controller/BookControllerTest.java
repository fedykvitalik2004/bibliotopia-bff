package org.vitalii.fedyk.book.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.vitalii.fedyk.book.dto.BookRequest;
import org.vitalii.fedyk.book.dto.BookResponse;
import org.vitalii.fedyk.book.mapper.BookRequestMapper;
import org.vitalii.fedyk.book.mapper.BookResponseMapper;
import org.vitalii.fedyk.book.model.Book;
import org.vitalii.fedyk.book.model.BookGenre;
import org.vitalii.fedyk.book.model.BookTranslation;
import org.vitalii.fedyk.book.model.CreateBookCommand;
import org.vitalii.fedyk.book.usecase.CreateBookUseCase;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

  @Mock private CreateBookUseCase createBookUseCase;

  @Mock private BookRequestMapper requestMapper;

  @Mock private BookResponseMapper responseMapper;

  @InjectMocks private BookController bookController;

  @Test
  void should_ReturnCreatedBook_when_RequestIsValid() {
    // Given
    final BookRequest request =
        new BookRequest(
            "978-3-16-148410-0",
            new BigDecimal("29.99"),
            List.of(new BookRequest.TranslationRequest("The Great Gatsby", "A classic novel")),
            List.of("Fiction"),
            List.of(1L, 2L));

    final CreateBookCommand command =
        CreateBookCommand.builder()
            .isbn("978-3-16-148410-0")
            .price(new BigDecimal("29.99"))
            .translations(
                List.of(
                    CreateBookCommand.TranslationCommand.builder()
                        .title("The Great Gatsby")
                        .description("A classic novel")
                        .build()))
            .genres(List.of("Fiction"))
            .authorIds(List.of(1L, 2L))
            .build();

    final Book savedBook =
        Book.restore(
            10L,
            "978-3-16-148410-0",
            240,
            "https://img.example.com/books/10.png",
            new BigDecimal("29.99"),
            List.of(new BookTranslation("The Great Gatsby", "A classic novel")),
            List.of(new BookGenre("Fiction")),
            List.of(1L, 2L));

    final BookResponse expectedResponse =
        new BookResponse(
            10L,
            "978-3-16-148410-0",
            240,
            "https://img.example.com/books/10.png",
            new BigDecimal("29.99"),
            List.of(new BookResponse.TranslationResponse("The Great Gatsby", "A classic novel")),
            List.of("Fiction"),
            List.of(1L, 2L));

    when(this.requestMapper.toCreateBookCommand(request)).thenReturn(command);
    when(this.createBookUseCase.create(command)).thenReturn(savedBook);
    when(this.responseMapper.toBookResponse(savedBook)).thenReturn(expectedResponse);

    // When
    final var response = this.bookController.create(request);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

    verify(this.requestMapper).toCreateBookCommand(request);
    verify(this.createBookUseCase).create(command);
    verify(this.responseMapper).toBookResponse(savedBook);
    verifyNoMoreInteractions(this.requestMapper, this.createBookUseCase, this.responseMapper);
  }
}

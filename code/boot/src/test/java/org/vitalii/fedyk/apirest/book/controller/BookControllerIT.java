package org.vitalii.fedyk.apirest.book.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.vitalii.fedyk.GlobalExceptionHandler;
import org.vitalii.fedyk.book.controller.BookController;
import org.vitalii.fedyk.book.dto.BookRequest;
import org.vitalii.fedyk.book.dto.BookRequest.TranslationRequest;
import org.vitalii.fedyk.book.dto.BookResponse;
import org.vitalii.fedyk.book.mapper.BookRequestMapper;
import org.vitalii.fedyk.book.mapper.BookResponseMapper;
import org.vitalii.fedyk.book.model.Book;
import org.vitalii.fedyk.book.model.CreateBookCommand;
import org.vitalii.fedyk.book.usecase.CreateBookUseCase;

@WebMvcTest(BookController.class)
@Import({GlobalExceptionHandler.class, ObjectMapper.class})
class BookControllerIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private CreateBookUseCase createBookUseCase;

  @MockitoBean private BookRequestMapper requestMapper;

  @MockitoBean private BookResponseMapper responseMapper;

  @Test
  void should_ReturnBadRequest_when_RequestIsInvalid() throws Exception {
    // Given
    final BookRequest invalidRequest = new BookRequest(null, null, List.of(), List.of(), List.of());

    // When
    this.mockMvc
        .perform(
            post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());

    // Then
    verifyNoInteractions(this.requestMapper);
    verifyNoInteractions(this.createBookUseCase);
    verifyNoInteractions(this.responseMapper);
  }

  @Test
  void should_CreateBookAndReturnCreatedStatus_when_RequestIsValid() throws Exception {
    // Given
    final TranslationRequest translationRequest =
        new TranslationRequest("Some title", "Some description");
    final BookRequest request =
        new BookRequest(
            "978-3-16-148410-0",
            BigDecimal.TEN,
            List.of(translationRequest),
            List.of("Fiction"),
            List.of(1L));

    final CreateBookCommand command = Instancio.create(CreateBookCommand.class);
    final Book savedBook = Instancio.create(Book.class);
    final BookResponse response = Instancio.create(BookResponse.class);

    when(this.requestMapper.toCreateBookCommand(any(BookRequest.class))).thenReturn(command);
    when(this.createBookUseCase.create(any(CreateBookCommand.class))).thenReturn(savedBook);
    when(this.responseMapper.toBookResponse(savedBook)).thenReturn(response);

    // When
    this.mockMvc
        .perform(
            post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(this.objectMapper.writeValueAsString(response)));

    // Then
    verify(this.requestMapper).toCreateBookCommand(request);
    verify(this.createBookUseCase).create(command);
    verify(this.responseMapper).toBookResponse(savedBook);
  }
}

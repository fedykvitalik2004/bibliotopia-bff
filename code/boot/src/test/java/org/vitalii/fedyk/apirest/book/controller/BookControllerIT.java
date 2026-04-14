package org.vitalii.fedyk.apirest.book.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.vitalii.fedyk.bibliotopiabff.application.book.dto.BookPricingResult;
import org.vitalii.fedyk.bibliotopiabff.application.book.dto.CreateBookCommand;
import org.vitalii.fedyk.bibliotopiabff.application.book.port.in.CreateBookUseCase;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.BookController;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.dto.request.BookRequest;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.dto.request.BookRequest.TranslationRequest;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.dto.response.BookResponse;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.mapper.BookWebRequestMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.mapper.BookWebResponseMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.common.in.rest.GlobalExceptionHandler;

@WebMvcTest(BookController.class)
@Import({GlobalExceptionHandler.class, ObjectMapper.class})
class BookControllerIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private CreateBookUseCase createBookUseCase;

  @MockitoBean private BookWebRequestMapper requestMapper;

  @MockitoBean private BookWebResponseMapper responseMapper;

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
        new TranslationRequest("Some title", "Some description", "ua");
    final BookRequest request =
        new BookRequest(
            "978-3-16-148410-0",
            BigDecimal.TEN,
            List.of(translationRequest),
            List.of("Fiction"),
            List.of(1L));

    final BigDecimal effectivePrice = new BigDecimal("80.00");
    final CreateBookCommand command = Instancio.create(CreateBookCommand.class);
    final Book savedBook = Instancio.create(Book.class);
    final BookPricingResult result = new BookPricingResult(savedBook, effectivePrice);
    final BookResponse response = Instancio.create(BookResponse.class);
    final ZoneId zoneId = ZoneId.of("America/New_York");

    when(this.requestMapper.toCreateBookCommand(any(BookRequest.class), eq(zoneId)))
        .thenReturn(command);
    when(this.createBookUseCase.create(any(CreateBookCommand.class))).thenReturn(result);
    when(this.responseMapper.toBookResponse(result)).thenReturn(response);

    // When
    this.mockMvc
        .perform(
            post("/api/v1/books")
                .header("X-Timezone", "America/New_York")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(this.objectMapper.writeValueAsString(response)));

    // Then
    verify(this.requestMapper).toCreateBookCommand(request, zoneId);
    verify(this.createBookUseCase).create(command);
    verify(this.responseMapper).toBookResponse(result);
  }
}

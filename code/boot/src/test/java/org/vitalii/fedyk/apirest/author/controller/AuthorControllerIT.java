package org.vitalii.fedyk.apirest.author.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.vitalii.fedyk.GlobalExceptionHandler;
import org.vitalii.fedyk.author.controller.AuthorController;
import org.vitalii.fedyk.author.dto.AuthorRequest;
import org.vitalii.fedyk.author.dto.AuthorResponse;
import org.vitalii.fedyk.author.dto.TranslationRequest;
import org.vitalii.fedyk.author.mapper.AuthorRequestMapper;
import org.vitalii.fedyk.author.mapper.AuthorResponseMapper;
import org.vitalii.fedyk.author.model.Author;
import org.vitalii.fedyk.author.usecase.CreateAuthorUseCase;
import org.vitalii.fedyk.author.usecase.GetAllAuthorsUseCase;
import org.vitalii.fedyk.author.usecase.GetAuthorByIdUseCase;

@WebMvcTest({AuthorController.class})
@Import(GlobalExceptionHandler.class)
class AuthorControllerIT {
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private CreateAuthorUseCase createAuthorUseCase;

  @MockitoBean private GetAllAuthorsUseCase getAllAuthorsUseCase;

  @MockitoBean private GetAuthorByIdUseCase getAuthorByIdUseCase;

  @MockitoBean private AuthorRequestMapper requestMapper;

  @MockitoBean private AuthorResponseMapper responseMapper;

  @Test
  void whenInvalidRequest_thenReturnsBadRequest() throws Exception {
    // Given
    final var localDate = LocalDate.now();
    final AuthorRequest request =
        AuthorRequest.builder().birthDate(localDate).deathDate(localDate.plusDays(5)).build();

    // When
    this.mockMvc
        .perform(
            post("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

    // Then
    verifyNoInteractions(this.requestMapper);
  }

  @Test
  void shouldCreateAuthorAndReturnCreatedStatus() throws Exception {
    // Given
    final var request =
        AuthorRequest.builder()
            .birthDate(LocalDate.MIN)
            .deathDate(LocalDate.MAX)
            .translations(Instancio.createList(TranslationRequest.class))
            .build();
    final var author = Instancio.create(Author.class);
    final var savedAuthor = Instancio.create(Author.class);
    final var response = Instancio.create(AuthorResponse.class);

    when(this.requestMapper.toAuthor(any(AuthorRequest.class))).thenReturn(author);
    when(this.createAuthorUseCase.createAuthor(any(Author.class))).thenReturn(savedAuthor);
    when(this.responseMapper.toResponse(savedAuthor)).thenReturn(response);

    // When
    this.mockMvc
        .perform(
            post("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(this.objectMapper.writeValueAsString(response)));

    // Then
    verify(this.requestMapper).toAuthor(request);
    verify(this.createAuthorUseCase).createAuthor(author);
    verify(this.responseMapper).toResponse(savedAuthor);
  }
}

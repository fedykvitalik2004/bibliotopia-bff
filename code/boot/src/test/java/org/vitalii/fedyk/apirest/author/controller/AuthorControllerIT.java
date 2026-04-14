package org.vitalii.fedyk.apirest.author.controller;

import static org.instancio.Select.field;
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
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.vitalii.fedyk.bibliotopiabff.application.author.port.in.CreateAuthorUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.author.port.in.GetAllAuthorsUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.author.port.in.GetAuthorByIdUseCase;
import org.vitalii.fedyk.bibliotopiabff.domain.author.model.Author;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.AuthorController;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.dto.request.AuthorRequest;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.dto.request.TranslationRequest;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.dto.response.AuthorResponse;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.mapper.AuthorRequestMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.mapper.AuthorResponseMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.common.in.rest.GlobalExceptionHandler;
import org.vitalii.fedyk.config.TestJacksonConfig;

@WebMvcTest({AuthorController.class})
@Import({GlobalExceptionHandler.class, TestJacksonConfig.class})
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
            .translations(
                Instancio.ofList(TranslationRequest.class)
                    .generate(field(TranslationRequest::language), gen -> gen.string().length(3, 4))
                    .create())
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

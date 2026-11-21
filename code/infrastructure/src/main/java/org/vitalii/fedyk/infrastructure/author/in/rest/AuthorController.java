package org.vitalii.fedyk.infrastructure.author.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vitalii.fedyk.application.author.port.in.CreateAuthorUseCase;
import org.vitalii.fedyk.application.author.port.in.GetAllAuthorsUseCase;
import org.vitalii.fedyk.application.author.port.in.GetAuthorByIdUseCase;
import org.vitalii.fedyk.domain.author.model.Author;
import org.vitalii.fedyk.infrastructure.author.in.rest.dto.request.AuthorRequest;
import org.vitalii.fedyk.infrastructure.author.in.rest.dto.response.AuthorResponse;
import org.vitalii.fedyk.infrastructure.author.in.rest.mapper.AuthorRequestMapper;
import org.vitalii.fedyk.infrastructure.author.in.rest.mapper.AuthorResponseMapper;
import org.vitalii.fedyk.infrastructure.common.in.rest.dto.ErrorDto;
import org.vitalii.fedyk.infrastructure.generated.api.AuthorsApi;

@RestController
@RequiredArgsConstructor
@Tag(name = "Author", description = "Endpoints for creating and retrieving authors")
public class AuthorController implements AuthorsApi {
  private final AuthorResponseMapper authorResponseMapper;

  private final AuthorRequestMapper authorRequestMapper;

  private final CreateAuthorUseCase createAuthorUseCase;

  private final GetAllAuthorsUseCase getAllAuthorsUseCase;

  private final GetAuthorByIdUseCase getAuthorByIdUseCase;

  @Operation(
      summary = "Create a new author",
      description = "Validates the request and creates a new author in the system")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Author created successfully",
        content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Validation error: Check the response body for localized messages",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @Override
  public ResponseEntity<AuthorResponse> createAuthor(@RequestBody final AuthorRequest request) {
    final Author author = this.authorRequestMapper.toAuthor(request);

    final Author savedAuthor = this.createAuthorUseCase.createAuthor(author);

    return new ResponseEntity<>(
        this.authorResponseMapper.toResponse(savedAuthor), HttpStatus.CREATED);
  }

  @Operation(summary = "Get all authors", description = "Retrieves a list of all authors")
  @ApiResponse(
      responseCode = "200",
      description = "List of authors retrieved successfully",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = AuthorResponse.class))))
  @Override
  public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
    final List<AuthorResponse> response =
        this.getAllAuthorsUseCase.getAllAuthors().stream()
            .map(this.authorResponseMapper::toResponse)
            .toList();

    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Get author by ID",
      description = "Retrieves a single author based on their unique identifier")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Author found",
        content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "Author not found",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @Override
  public ResponseEntity<AuthorResponse> getAuthorById(final Long id) {
    return this.getAuthorByIdUseCase
        .getAuthor(id)
        .map(author -> ResponseEntity.ok(this.authorResponseMapper.toResponse(author)))
        .orElse(ResponseEntity.notFound().build());
  }
}

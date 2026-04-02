package org.vitalii.fedyk.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vitalii.fedyk.book.dto.BookRequest;
import org.vitalii.fedyk.book.dto.BookResponse;
import org.vitalii.fedyk.book.mapper.BookRequestMapper;
import org.vitalii.fedyk.book.mapper.BookResponseMapper;
import org.vitalii.fedyk.book.model.Book;
import org.vitalii.fedyk.book.model.CreateBookCommand;
import org.vitalii.fedyk.book.usecase.CreateBookUseCase;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Book", description = "Endpoints for creating and managing book records")
public class BookController {
  private final CreateBookUseCase createBookUseCase;

  private final BookRequestMapper requestMapper;

  private final BookResponseMapper responseMapper;

  @PostMapping
  @Operation(
      summary = "Create a new book",
      description = "Registers a new book with localized validation rules")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Book created successfully",
        content = @Content(schema = @Schema(implementation = BookResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Validation failed: Check the localized error details",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  /**
   * Creates a new book based on the provided request.
   *
   * @param request The book request containing the details of the book to be created. Must be a
   *     valid request.
   * @return A ResponseEntity containing the created book and a status of 201 (Created).
   */
  public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
    final CreateBookCommand command = this.requestMapper.toCreateBookCommand(request);
    final Book savedBook = this.createBookUseCase.create(command);
    return new ResponseEntity<>(this.responseMapper.toBookResponse(savedBook), HttpStatus.CREATED);
  }
}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.common.in.rest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.vitalii.fedyk.bibliotopiabff.domain.book.exception.BookNotFoundException;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.common.in.rest.dto.ErrorDto;

import static org.vitalii.fedyk.bibliotopiabff.infrastructure.common.ExceptionMessageConstants.BOOK_NOT_FOUND;
import static org.vitalii.fedyk.bibliotopiabff.infrastructure.common.ExceptionMessageConstants.SERVICE_UNAVAILABLE;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
  private MessageSource messageSource;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    final Map<String, String> fieldErrors = new HashMap<>();
    exception
        .getBindingResult()
        .getFieldErrors()
        .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

    return ErrorDto.builder().title("Invalid request data").additionalParams(fieldErrors).build();
  }

  @ExceptionHandler(BookNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDto handleBookNotFoundException(final BookNotFoundException exception, final Locale locale) {
    return ErrorDto.builder()
            .title(this.messageSource.getMessage(BOOK_NOT_FOUND, new Object[] {exception.getIsbn()}, locale))
            .build();
  }

  @ExceptionHandler(CallNotPermittedException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public ErrorDto handleCallNotPermittedException(final Locale locale) {
    return ErrorDto.builder()
            .title(this.messageSource.getMessage(SERVICE_UNAVAILABLE, null, locale))
            .build();
  }

}

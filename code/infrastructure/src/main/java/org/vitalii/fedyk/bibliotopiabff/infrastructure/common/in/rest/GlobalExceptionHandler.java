package org.vitalii.fedyk.bibliotopiabff.infrastructure.common.in.rest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.vitalii.fedyk.bibliotopiabff.domain.currency.exception.CurrencyServiceException;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.common.in.rest.dto.ErrorDto;

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

  @ExceptionHandler(CurrencyServiceException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public ErrorDto handleCurrencyServiceException(
      final CurrencyServiceException exception, final Locale locale) {
    final String resolvedMessage =
        this.messageSource.getMessage(exception.getMessage(), null, locale);
    return ErrorDto.builder().title(resolvedMessage).additionalParams(null).build();
  }
}

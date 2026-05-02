package org.vitalii.fedyk.bibliotopiabff.domain.book.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookNotFoundException extends RuntimeException {
  private final String isbn;

  public BookNotFoundException(String isbn) {
    super(String.format("Book with ISBN %s not found in external catalog", isbn));
    this.isbn = isbn;
  }
}

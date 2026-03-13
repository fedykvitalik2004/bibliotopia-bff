package org.vitalii.fedyk.book.usecase;

import org.vitalii.fedyk.book.model.Book;
import org.vitalii.fedyk.book.model.CreateBookCommand;

public interface CreateBookUseCase {
  Book create(CreateBookCommand createBookCommand);
}

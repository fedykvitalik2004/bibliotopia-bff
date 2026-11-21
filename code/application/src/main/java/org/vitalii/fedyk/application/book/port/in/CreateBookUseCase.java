package org.vitalii.fedyk.application.book.port.in;

import org.vitalii.fedyk.application.book.dto.BookPricingResult;
import org.vitalii.fedyk.application.book.dto.CreateBookCommand;

public interface CreateBookUseCase {
  BookPricingResult create(CreateBookCommand createBookCommand);
}

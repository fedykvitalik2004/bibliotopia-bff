package org.vitalii.fedyk.bibliotopiabff.application.book.port.in;

import org.vitalii.fedyk.bibliotopiabff.application.book.dto.BookPricingResult;
import org.vitalii.fedyk.bibliotopiabff.application.book.dto.CreateBookCommand;

public interface CreateBookUseCase {
  BookPricingResult create(CreateBookCommand createBookCommand);
}

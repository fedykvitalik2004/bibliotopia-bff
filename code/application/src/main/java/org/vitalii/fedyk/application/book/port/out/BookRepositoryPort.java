package org.vitalii.fedyk.application.book.port.out;

import org.vitalii.fedyk.domain.book.model.Book;

public interface BookRepositoryPort {
  Book save(Book book);
}

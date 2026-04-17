package org.vitalii.fedyk.bibliotopiabff.application.book.port.out;

import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;

public interface BookRepositoryPort {
  Book save(Book book);
}

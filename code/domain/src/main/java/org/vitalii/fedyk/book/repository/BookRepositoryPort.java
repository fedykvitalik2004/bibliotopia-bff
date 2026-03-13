package org.vitalii.fedyk.book.repository;

import org.vitalii.fedyk.book.model.Book;

public interface BookRepositoryPort {
  Book save(Book book);
}

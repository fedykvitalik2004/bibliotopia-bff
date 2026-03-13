package org.vitalii.fedyk.book.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.book.dto.BookResponse;
import org.vitalii.fedyk.book.model.Book;
import org.vitalii.fedyk.book.model.BookGenre;

@Mapper(componentModel = "spring")
public interface BookResponseMapper {
  BookResponse toBookResponse(Book book);

  default String mapGenre(BookGenre genre) {
    return genre != null ? genre.genre() : null;
  }
}

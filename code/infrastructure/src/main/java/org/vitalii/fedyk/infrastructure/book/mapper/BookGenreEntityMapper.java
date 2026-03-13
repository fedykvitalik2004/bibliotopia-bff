package org.vitalii.fedyk.infrastructure.book.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.vitalii.fedyk.book.model.BookGenre;
import org.vitalii.fedyk.infrastructure.book.jpa.BookGenreEntity;

@Mapper(componentModel = "spring")
public interface BookGenreEntityMapper {
  BookGenreEntity toBookGenreEntity(BookGenre genre);

  BookGenre toBookGenre(BookGenreEntity genreEntity);

  List<BookGenre> toBookGenres(List<BookGenreEntity> genreEntities);
}

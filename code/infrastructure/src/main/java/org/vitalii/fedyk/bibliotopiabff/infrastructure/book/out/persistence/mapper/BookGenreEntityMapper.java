package org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.BookGenre;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.entity.BookGenreEntity;

@Mapper(componentModel = "spring")
public interface BookGenreEntityMapper {
  BookGenreEntity toBookGenreEntity(BookGenre genre);

  BookGenre toBookGenre(BookGenreEntity genreEntity);

  List<BookGenre> toBookGenres(List<BookGenreEntity> genreEntities);
}

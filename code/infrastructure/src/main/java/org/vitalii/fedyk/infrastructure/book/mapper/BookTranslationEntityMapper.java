package org.vitalii.fedyk.infrastructure.book.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.vitalii.fedyk.book.model.BookTranslation;
import org.vitalii.fedyk.infrastructure.book.jpa.BookTranslationEntity;

@Mapper(componentModel = "spring")
public interface BookTranslationEntityMapper {
  BookTranslationEntity toBookTranslationEntity(final BookTranslation book);

  BookTranslation toBookTranslation(BookTranslationEntity bookTranslationEntity);

  List<BookTranslation> toBookTranslations(List<BookTranslationEntity> bookTranslationEntities);
}

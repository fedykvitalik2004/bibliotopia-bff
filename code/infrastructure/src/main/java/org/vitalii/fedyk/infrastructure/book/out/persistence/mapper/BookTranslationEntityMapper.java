package org.vitalii.fedyk.infrastructure.book.out.persistence.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.vitalii.fedyk.domain.book.model.BookTranslation;
import org.vitalii.fedyk.infrastructure.book.out.persistence.entity.BookTranslationEntity;

@Mapper(componentModel = "spring")
public interface BookTranslationEntityMapper {
  BookTranslationEntity toBookTranslationEntity(final BookTranslation book);

  BookTranslation toBookTranslation(BookTranslationEntity bookTranslationEntity);

  List<BookTranslation> toBookTranslations(List<BookTranslationEntity> bookTranslationEntities);
}

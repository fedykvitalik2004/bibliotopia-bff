package org.vitalii.fedyk.infrastructure.author.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.author.Author;
import org.vitalii.fedyk.infrastructure.author.AuthorEntity;

@Mapper(componentModel = "spring", uses = AuthorTranslationMapper.class)
public interface AuthorMapper {
  AuthorEntity toEntity(Author author);

  Author fromEntity(AuthorEntity authorEntity);
}

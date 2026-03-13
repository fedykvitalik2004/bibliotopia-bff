package org.vitalii.fedyk.infrastructure.author.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.author.model.Author;
import org.vitalii.fedyk.infrastructure.author.jpa.AuthorEntity;

@Mapper(componentModel = "spring")
public interface AuthorEntityMapper {
  AuthorEntity toAuthorEntity(final Author author);

  Author toAuthor(final AuthorEntity authorEntity);
}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.bibliotopiabff.domain.author.model.Author;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.entity.AuthorEntity;

@Mapper(componentModel = "spring")
public interface AuthorEntityMapper {
  AuthorEntity toAuthorEntity(final Author author);

  Author toAuthor(final AuthorEntity authorEntity);
}

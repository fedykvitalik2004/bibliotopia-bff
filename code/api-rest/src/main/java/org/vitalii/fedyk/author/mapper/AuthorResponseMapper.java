package org.vitalii.fedyk.author.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.author.dto.AuthorResponse;
import org.vitalii.fedyk.author.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorResponseMapper {
  AuthorResponse toResponse(Author author);
}

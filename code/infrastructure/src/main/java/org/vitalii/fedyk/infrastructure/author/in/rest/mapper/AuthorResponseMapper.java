package org.vitalii.fedyk.infrastructure.author.in.rest.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.domain.author.model.Author;
import org.vitalii.fedyk.infrastructure.author.in.rest.dto.response.AuthorResponse;

@Mapper(componentModel = "spring")
public interface AuthorResponseMapper {
  AuthorResponse toResponse(Author author);
}

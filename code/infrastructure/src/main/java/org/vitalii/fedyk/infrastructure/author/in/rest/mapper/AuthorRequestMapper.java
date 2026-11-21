package org.vitalii.fedyk.infrastructure.author.in.rest.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.domain.author.model.Author;
import org.vitalii.fedyk.infrastructure.author.in.rest.dto.request.AuthorRequest;

@Mapper(componentModel = "spring")
public interface AuthorRequestMapper {
  Author toAuthor(AuthorRequest authorRequest);
}

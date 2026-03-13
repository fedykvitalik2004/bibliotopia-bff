package org.vitalii.fedyk.author.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.author.dto.AuthorRequest;
import org.vitalii.fedyk.author.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorRequestMapper {
  Author toAuthor(AuthorRequest authorRequest);
}

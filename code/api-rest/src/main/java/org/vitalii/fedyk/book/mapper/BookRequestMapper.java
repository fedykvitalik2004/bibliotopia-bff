package org.vitalii.fedyk.book.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.book.dto.BookRequest;
import org.vitalii.fedyk.book.model.CreateBookCommand;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {
  CreateBookCommand toCreateBookCommand(BookRequest bookRequest);
}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.mapper;

import java.time.ZoneId;
import org.mapstruct.Mapper;
import org.vitalii.fedyk.bibliotopiabff.application.book.dto.CreateBookCommand;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.dto.request.BookRequest;

@Mapper(componentModel = "spring")
public interface BookWebRequestMapper {
  CreateBookCommand toCreateBookCommand(BookRequest bookRequest, ZoneId zoneId);
}

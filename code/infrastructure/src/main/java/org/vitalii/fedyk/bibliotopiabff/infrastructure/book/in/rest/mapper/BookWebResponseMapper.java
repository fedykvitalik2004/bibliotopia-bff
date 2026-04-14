package org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.vitalii.fedyk.bibliotopiabff.application.book.dto.BookPricingResult;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.BookGenre;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.dto.response.BookResponse;

@Mapper(componentModel = "spring")
public interface BookWebResponseMapper {
  @Mapping(target = "id", source = "book.id")
  @Mapping(target = "isbn", source = "book.isbn")
  @Mapping(target = "pageCount", source = "book.pageCount")
  @Mapping(target = "coverImageUrl", source = "book.coverImageUrl")
  @Mapping(target = "price", source = "book.price")
  @Mapping(target = "finalPrice", source = "finalPrice")
  @Mapping(target = "translations", source = "book.translations")
  @Mapping(target = "genres", source = "book.genres")
  @Mapping(target = "authorIds", source = "book.authorIds")
  BookResponse toBookResponse(BookPricingResult bookPricingResult);

  default String mapGenre(BookGenre genre) {
    return genre != null ? genre.genre() : null;
  }
}

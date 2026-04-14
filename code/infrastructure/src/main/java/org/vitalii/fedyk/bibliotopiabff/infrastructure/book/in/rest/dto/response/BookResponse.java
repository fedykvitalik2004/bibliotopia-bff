package org.vitalii.fedyk.bibliotopiabff.infrastructure.book.in.rest.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record BookResponse(
    Long id,
    String isbn,
    int pageCount,
    String coverImageUrl,
    BigDecimal price,
    List<TranslationResponse> translations,
    List<String> genres,
    List<Long> authorIds,
    BigDecimal finalPrice) {
  public record TranslationResponse(String title, String description) {}
}

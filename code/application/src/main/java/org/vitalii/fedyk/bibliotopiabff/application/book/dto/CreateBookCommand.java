package org.vitalii.fedyk.bibliotopiabff.application.book.dto;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateBookCommand(
    String isbn,
    BigDecimal price,
    List<TranslationCommand> translations,
    List<String> genres,
    List<Long> authorIds,
    ZoneId zoneId) {
  @Builder
  public record TranslationCommand(String title, String description, String language) {}
}

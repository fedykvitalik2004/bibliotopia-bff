package org.vitalii.fedyk.book.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateBookCommand(
    String isbn,
    BigDecimal price,
    List<TranslationCommand> translations,
    List<String> genres,
    List<Long> authorIds) {
  @Builder
  public record TranslationCommand(String title, String description) {}
}

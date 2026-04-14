package org.vitalii.fedyk.bibliotopiabff.domain.book.model;

import java.util.Objects;

public record BookTranslation(String title, String description, String language) {
  public BookTranslation {
    Objects.requireNonNull(title, "Title cannot be null");
    Objects.requireNonNull(description, "Description cannot be null");
    Objects.requireNonNull(language, "Language cannot be null");
  }
}

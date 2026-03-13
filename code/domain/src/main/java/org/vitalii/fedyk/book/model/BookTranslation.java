package org.vitalii.fedyk.book.model;

import java.util.Objects;

public record BookTranslation(String title, String description) {
  public BookTranslation {
    Objects.requireNonNull(title, "Title cannot be null");
    Objects.requireNonNull(description, "Description cannot be null");
  }
}

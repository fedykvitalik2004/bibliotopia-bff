package org.vitalii.fedyk.common.author;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Author {
  private UUID id;
  private LocalDate birthDate;
  private LocalDate deathDate;
  private List<AuthorTranslation> translations;
  private List<Book> books;
}

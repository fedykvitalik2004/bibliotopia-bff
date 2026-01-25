package org.vitalii.fedyk.infrastructure.author;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.vitalii.fedyk.infrastructure.BaseEntity;
import org.vitalii.fedyk.infrastructure.book.Book;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
public class Author extends BaseEntity {
  private LocalDate birthDate;

  private LocalDate deathDate;

  @OneToMany(mappedBy = "author")
  private List<AuthorTranslation> authorTranslationList;

  @ManyToMany(mappedBy = "authors")
  private List<Book> books;
}

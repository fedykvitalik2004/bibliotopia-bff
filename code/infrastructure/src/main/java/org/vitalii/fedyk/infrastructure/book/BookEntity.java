package org.vitalii.fedyk.infrastructure.book;

import jakarta.persistence.*;
import org.vitalii.fedyk.infrastructure.author.Author;
import org.vitalii.fedyk.infrastructure.BaseEntity;
import org.vitalii.fedyk.infrastructure.bookpricehistory.BookPriceHistory;
import org.vitalii.fedyk.infrastructure.enumeration.BookGenre;
import org.vitalii.fedyk.infrastructure.enumeration.converter.BookGenreConverter;

import java.util.List;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

  private String isbn;

  private String title;

  @OneToMany(mappedBy = "book")
  private List<BookPriceHistory> priceHistories;

  @ManyToMany
  @JoinTable(
          name = "book_authors",
          joinColumns = @JoinColumn(name = "book_id"),
          inverseJoinColumns = @JoinColumn(name = "author_id")
  )
  private List<Author> authors;

  private int pageCount;

  private String coverImageUrl;

  @Convert(converter = BookGenreConverter.class)
  private BookGenre bookGenre;
}

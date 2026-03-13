package org.vitalii.fedyk.book.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
  private Long id;
  @EqualsAndHashCode.Include private String isbn;
  private int pageCount;
  private String coverImageUrl;
  private BigDecimal price;
  private List<BookTranslation> translations;
  private List<BookGenre> genres;
  private List<Long> authorIds;

  public static Book create(
      final String isbn,
      final int pageCount,
      final String coverImageUrl,
      final BigDecimal price,
      final List<BookTranslation> translations,
      final List<BookGenre> genres,
      final List<Long> authorIds) {
    validateCollections(translations, genres, authorIds);

    final Book book = new Book();
    book.isbn = isbn;
    book.pageCount = validatePageCount(pageCount);
    book.coverImageUrl = coverImageUrl;
    book.price = validatePrice(price);
    book.translations = List.copyOf(translations);
    book.genres = List.copyOf(genres);
    book.authorIds = List.copyOf(authorIds);

    return book;
  }

  public static Book restore(
      final Long id,
      final String isbn,
      final int pageCount,
      final String coverImageUrl,
      final BigDecimal price,
      final List<BookTranslation> translations,
      final List<BookGenre> genres,
      final List<Long> authorIds) {
    final Book book = new Book();
    book.id = id;
    book.isbn = isbn;
    book.pageCount = pageCount;
    book.coverImageUrl = coverImageUrl;
    book.price = price;
    book.translations = List.copyOf(translations);
    book.genres = List.copyOf(genres);
    book.authorIds = List.copyOf(authorIds);
    return book;
  }

  private static void validateCollections(List<?>... lists) {
    for (List<?> list : lists) {
      if (Objects.isNull(list) || list.isEmpty()) {
        throw new IllegalArgumentException("Required collection cannot be null or empty");
      }
    }
  }

  private static int validatePageCount(int pageCount) {
    if (pageCount <= 0) {
      throw new IllegalArgumentException("Page count should be greater than 0");
    }
    return pageCount;
  }

  private static BigDecimal validatePrice(final BigDecimal price) {
    if (price.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Page count should be greater than 0");
    }
    return price;
  }

  public List<BookGenre> getGenres() {
    return new ArrayList<>(this.genres);
  }

  public List<BookTranslation> getTranslations() {
    return new ArrayList<>(this.translations);
  }

  public List<Long> getAuthorIds() {
    return new ArrayList<>(this.authorIds);
  }
}

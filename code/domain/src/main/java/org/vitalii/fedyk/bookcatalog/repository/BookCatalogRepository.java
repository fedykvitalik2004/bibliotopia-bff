package org.vitalii.fedyk.bookcatalog.repository;

import java.util.Optional;
import org.vitalii.fedyk.bookcatalog.model.BookIsbnProjection;

/**
 * Repository interface for retrieving book details by ISBN.
 *
 * <p>This interface defines the contract for fetching book information from a data source, such as
 * a database or external API, using the book's ISBN as the key.
 */
public interface BookCatalogRepository {

  /**
   * Retrieves detailed information about a book based on its ISBN.
   *
   * @param isbn the International Standard Book Number (ISBN) of the book
   * @return an {@link Optional} containing {@link BookIsbnProjection} if a book with the given ISBN
   *     exists, or {@link Optional#empty()} if not found
   * @throws IllegalArgumentException if {@code isbn} is null or empty
   */
  Optional<BookIsbnProjection> getBookDetail(final String isbn);
}

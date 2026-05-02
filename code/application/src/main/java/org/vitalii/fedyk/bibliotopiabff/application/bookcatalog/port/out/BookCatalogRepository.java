package org.vitalii.fedyk.bibliotopiabff.application.bookcatalog.port.out;

import org.vitalii.fedyk.bibliotopiabff.application.bookcatalog.dto.BookIsbnProjection;

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
   * @return a {@link BookIsbnProjection}
   */
  BookIsbnProjection getBookDetail(final String isbn);
}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.bookcatalog.out.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.net.http.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.bibliotopiabff.application.bookcatalog.dto.BookIsbnProjection;
import org.vitalii.fedyk.bibliotopiabff.application.bookcatalog.port.out.BookCatalogRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.book.exception.BookNotFoundException;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.bookcatalog.out.rest.client.BookCatalogApiClient;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.common.exception.ExternalServiceException;

@Repository
@AllArgsConstructor
@Slf4j
public class BookCatalogRestAdapter implements BookCatalogRepository {
  private final BookCatalogApiClient bookCatalogApiClient;

  private final ObjectMapper objectMapper;

  private static final String fieldPrefix = "ISBN:";

  @Override
  @CircuitBreaker(name = "bookCatalog")
  public BookIsbnProjection getBookDetail(final String isbn) {
    final HttpResponse<String> response = this.bookCatalogApiClient.findBooksMetadata(isbn);
    final int statusCode = response.statusCode();

    if (statusCode == 200) {
      try {
        final JsonNode mainNode = this.objectMapper.readTree(response.body());
        return parseBookDetails(mainNode, isbn);
      } catch (JsonProcessingException exception) {
        log.error(
            "BookIsbnProjection parsing failed for ISBN: {}. Error: {}",
            isbn,
            exception.getMessage(),
            exception);
        throw new ExternalServiceException("Failed to parse external book metadata", exception);
      }
    }

    log.error(
        "External API call failed for ISBN: {}. Status code: {}", isbn, response.statusCode());
    if (response.statusCode() == 404) {
      throw new BookNotFoundException(isbn); // Domain exception. The Circuit Breaker ignores it.
    }
    throw new ExternalServiceException(
        "External service error while retrieving BookIsbnProjection: " + response.body());
  }

  private BookIsbnProjection parseBookDetails(final JsonNode node, final String isbn) {
    final JsonNode mainNode = node.get(fieldPrefix + isbn);

    final String thumbnailsUrl = mainNode.get("thumbnail_url").asText();
    final JsonNode detailsNode = mainNode.get("details");
    final String title = detailsNode.get("title").asText();
    final int publishDate = detailsNode.get("publish_date").asInt();
    final int numberOfPages = detailsNode.get("number_of_pages").asInt();
    return new BookIsbnProjection(title, publishDate, numberOfPages, thumbnailsUrl);
  }
}

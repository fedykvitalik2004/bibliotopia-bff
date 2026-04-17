package org.vitalii.fedyk.bibliotopiabff.infrastructure.bookcatalog.out.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.bibliotopiabff.application.bookcatalog.dto.BookIsbnProjection;
import org.vitalii.fedyk.bibliotopiabff.application.bookcatalog.port.out.BookCatalogRepository;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.bookcatalog.out.rest.client.BookCatalogApiClient;

@Repository
@AllArgsConstructor
@Slf4j
public class BookCatalogRestAdapter implements BookCatalogRepository {
  private final BookCatalogApiClient bookCatalogApiClient;

  private final ObjectMapper objectMapper;

  private static final String fieldPrefix = "ISBN:";

  @Override
  public Optional<BookIsbnProjection> getBookDetail(final String isbn) {
    try {
      final HttpResponse<String> response = this.bookCatalogApiClient.findBooksMetadata(isbn);

      if (response.statusCode() == 200) {
        final JsonNode mainNode = this.objectMapper.readTree(response.body());
        return parseBookDetails(mainNode, isbn);
      }

      log.warn(
          "External API call failed for ISBN: {}. Status code: {}", isbn, response.statusCode());
      return Optional.empty();
    } catch (Exception e) {
      log.warn("Retrieved error while calling externalBookClient: {}", e.getMessage());
      return Optional.empty();
    }
  }

  private Optional<BookIsbnProjection> parseBookDetails(final JsonNode node, final String isbn) {
    final JsonNode mainNode = node.get(fieldPrefix + isbn);

    final String thumbnailsUrl = mainNode.get("thumbnail_url").asText();
    final JsonNode detailsNode = mainNode.get("details");
    final String title = detailsNode.get("title").asText();
    final int publishDate = detailsNode.get("publish_date").asInt();
    final int numberOfPages = detailsNode.get("number_of_pages").asInt();

    return Optional.of(new BookIsbnProjection(title, publishDate, numberOfPages, thumbnailsUrl));
  }
}

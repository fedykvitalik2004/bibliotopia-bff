package org.vitalii.fedyk.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.vitalii.fedyk.book.BookIsbnProjection;
import org.vitalii.fedyk.book.repository.BookIsbnRepository;

@Repository
@AllArgsConstructor
@Slf4j
public class BookIsbnRepositoryImpl implements BookIsbnRepository {
  private final RestClient externalBookClient;

  private final String FIELD_PREFIX = "ISBN:";

  @Override
  public Optional<BookIsbnProjection> getBookDetail(final String isbn) {
    try {
      final URI uri = UriComponentsBuilder.fromPath("/api/books")
              .queryParam("bibkeys", FIELD_PREFIX + isbn)
              .queryParam("jscmd", "details")
              .queryParam("format", "json")
              .build()
              .toUri();
      final JsonNode mainNode = externalBookClient.get()
              .uri(uri)
              .retrieve()
              .body(JsonNode.class);

      return parseBookDetails(mainNode, isbn);
    } catch (Exception e) {
      log.warn("Retrieved error while calling externalBookClient: {}", e.getMessage());
      return Optional.empty();
    }
  }

  private Optional<BookIsbnProjection> parseBookDetails(final JsonNode node, final String isbn) {
    final JsonNode mainNode = node.get(FIELD_PREFIX + isbn);

    final String thumbnailsUrl = mainNode.get("thumbnail_url").asText();
    final JsonNode detailsNode = mainNode.get("details");
    final String title = detailsNode.get("title").asText();
    final int publishDate = detailsNode.get("publish_date").asInt();
    final String description = detailsNode.get("description").asText();

    return Optional.of(new BookIsbnProjection(title, description, publishDate, thumbnailsUrl));
  }
}

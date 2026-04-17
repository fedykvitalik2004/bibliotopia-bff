package org.vitalii.fedyk.bibliotopiabff.infrastructure.bookcatalog.out.rest.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class BookCatalogApiClient {
  private final String baseUrl;

  private final HttpClient httpClient;

  private static final String FIELD_PREFIX = "ISBN:";

  public BookCatalogApiClient(
      @Value("${apis.book-catalog.base}") String baseUrl,
      @Qualifier("bookCatalogClient") final HttpClient httpClient) {
    this.baseUrl = baseUrl;
    this.httpClient = httpClient;
  }

  @SneakyThrows
  public HttpResponse<String> findBooksMetadata(final String isbn) {
    final URI uri =
        UriComponentsBuilder.fromUriString(baseUrl)
            .path("/api/books")
            .queryParam("bibkeys", FIELD_PREFIX + isbn)
            .queryParam("jscmd", "details")
            .queryParam("format", "json")
            .build()
            .toUri();
    final HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(uri).build();

    return this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
  }
}

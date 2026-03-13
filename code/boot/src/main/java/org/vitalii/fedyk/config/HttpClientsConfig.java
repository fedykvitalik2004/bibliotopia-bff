package org.vitalii.fedyk.config;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientsConfig {
  @Bean
  public HttpClient bookCatalogClient() {
    return HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(7))
        .followRedirects(HttpClient.Redirect.NORMAL)
        .version(HttpClient.Version.HTTP_1_1)
        .build();
  }
}

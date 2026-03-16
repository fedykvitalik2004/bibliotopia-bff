package org.vitalii.fedyk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientsConfig {
  @Bean
  public RestClient currencyRestClient(@Value("${apis.currency.base}") final String baseUrl) {
    return RestClient.builder()
            .baseUrl(baseUrl)
            .build();
  }
}

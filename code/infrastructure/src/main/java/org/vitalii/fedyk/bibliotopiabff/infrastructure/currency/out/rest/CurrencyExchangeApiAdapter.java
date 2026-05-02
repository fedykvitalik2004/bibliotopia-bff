package org.vitalii.fedyk.bibliotopiabff.infrastructure.currency.out.rest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.vitalii.fedyk.bibliotopiabff.application.currency.port.out.CurrencyExchangeRateRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.currency.model.ExchangeRates;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.common.exception.ExternalServiceException;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.currency.out.rest.dto.CurrencyResponseDto;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeApiAdapter implements CurrencyExchangeRateRepository {
  private final RestClient client;

  @Value("${apis.currency.key}")
  private String key;

  @Override
  @CircuitBreaker(name = "currencyExchange")
  public ExchangeRates getRates(final String baseCurrency) {
    final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("base", baseCurrency);
    params.add("output", "json");
    params.add("key", key);

    final CurrencyResponseDto responseDto =
        this.client
            .get()
            .uri(uriBuilder -> uriBuilder.path("/v2/rates").queryParams(params).build())
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                (request, response) -> {
                  final String errorBody =
                      new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);

                  log.error(
                      "External API error: status {} and body {}",
                      response.getStatusCode().value(),
                      errorBody);
                  throw new ExternalServiceException("Failed to fetch rates");
                })
            .body(CurrencyResponseDto.class);

    return ExchangeRates.builder()
        .baseCurrency(responseDto.base())
        .rates(responseDto.rates())
        .build();
  }
}

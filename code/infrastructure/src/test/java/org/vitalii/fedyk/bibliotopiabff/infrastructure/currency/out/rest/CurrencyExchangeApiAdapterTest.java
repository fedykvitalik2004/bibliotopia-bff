package org.vitalii.fedyk.bibliotopiabff.infrastructure.currency.out.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.currency.out.rest.dto.CurrencyResponseDto;

@ExtendWith(MockitoExtension.class)
class CurrencyExchangeApiAdapterTest {
  @Mock private RestClient client;

  @Mock private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

  @Mock private RestClient.ResponseSpec responseSpec;

  @InjectMocks private CurrencyExchangeApiAdapter adapter;

  @Test
  void shouldReturnExchangeRatesWhenApiSucceeds() {
    // Given
    final var base = "USD";
    final var rates = Map.of("UAH", new BigDecimal("40.0"));
    final var dto =
        Instancio.of(CurrencyResponseDto.class)
            .set(field(CurrencyResponseDto::base), base)
            .set(field(CurrencyResponseDto::rates), rates)
            .create();

    when(client.get()).thenReturn(requestHeadersUriSpec);
    when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersUriSpec);
    when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
    when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
    when(responseSpec.body(CurrencyResponseDto.class)).thenReturn(dto);

    // When
    final var result = adapter.getRates(base);

    // Then
    assertThat(result.baseCurrency()).isEqualTo(base);
    assertThat(result.rates()).isEqualTo(rates);

    verify(client).get();
  }
}

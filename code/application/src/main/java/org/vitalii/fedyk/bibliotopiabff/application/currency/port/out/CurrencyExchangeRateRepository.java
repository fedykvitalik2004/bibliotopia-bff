package org.vitalii.fedyk.bibliotopiabff.application.currency.port.out;

import org.vitalii.fedyk.bibliotopiabff.domain.currency.model.ExchangeRates;

public interface CurrencyExchangeRateRepository {
  ExchangeRates getRates(String baseCurrency);
}

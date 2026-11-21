package org.vitalii.fedyk.application.currency.port.out;

import org.vitalii.fedyk.domain.currency.model.ExchangeRates;

public interface CurrencyExchangeRateRepository {
  ExchangeRates getRates(String baseCurrency);
}

package org.vitalii.fedyk.currency.repository;

import org.vitalii.fedyk.currency.model.ExchangeRates;

public interface CurrencyExchangeRateRepository {
  ExchangeRates getRates(String baseCurrency);
}

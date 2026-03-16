package org.vitalii.fedyk.currency.repository;

import org.vitalii.fedyk.currency.model.ExchangeRates;

import java.util.List;

public interface CurrencyExchangeRateRepository {
  ExchangeRates getRates(String baseCurrency);
}

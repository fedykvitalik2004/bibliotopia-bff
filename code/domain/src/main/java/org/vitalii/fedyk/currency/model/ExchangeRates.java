package org.vitalii.fedyk.currency.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public record ExchangeRates(String baseCurrency, Map<String, BigDecimal> rates) {
}

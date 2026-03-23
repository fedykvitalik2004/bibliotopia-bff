package org.vitalii.fedyk.currency.model;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;

@Builder
public record ExchangeRates(String baseCurrency, Map<String, BigDecimal> rates) {}

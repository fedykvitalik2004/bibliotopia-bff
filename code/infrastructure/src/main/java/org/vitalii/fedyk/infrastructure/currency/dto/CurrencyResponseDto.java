package org.vitalii.fedyk.infrastructure.currency.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrencyResponseDto(
    boolean valid, long updated, String base, Map<String, BigDecimal> rates) {}

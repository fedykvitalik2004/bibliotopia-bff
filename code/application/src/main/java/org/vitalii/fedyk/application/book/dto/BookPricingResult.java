package org.vitalii.fedyk.application.book.dto;

import java.math.BigDecimal;
import org.vitalii.fedyk.domain.book.model.Book;

public record BookPricingResult(Book book, BigDecimal finalPrice) {}

package org.vitalii.fedyk.bibliotopiabff.application.book.dto;

import java.math.BigDecimal;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;

public record BookPricingResult(Book book, BigDecimal finalPrice) {}

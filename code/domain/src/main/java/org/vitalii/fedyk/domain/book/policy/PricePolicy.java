package org.vitalii.fedyk.domain.book.policy;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.vitalii.fedyk.domain.book.model.Book;

public interface PricePolicy {
  boolean isApplicable(Book book, ZonedDateTime now);

  BigDecimal apply(BigDecimal price);
}

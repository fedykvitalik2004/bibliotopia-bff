package org.vitalii.fedyk.bibliotopiabff.domain.book.policy;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;

public interface PricePolicy {
  boolean isApplicable(Book book, ZonedDateTime now);

  BigDecimal apply(BigDecimal price);
}

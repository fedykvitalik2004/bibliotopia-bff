package org.vitalii.fedyk.bibliotopiabff.domain.book.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;
import org.vitalii.fedyk.bibliotopiabff.domain.book.policy.PricePolicy;

@AllArgsConstructor
public class PricingService {
  private final List<PricePolicy> policies;

  public BigDecimal calculatePrice(final Book book, final ZoneId zoneId) {
    final ZonedDateTime now = Instant.now().atZone(zoneId);

    BigDecimal price = book.getPrice();
    for (PricePolicy policy : policies) {
      if (policy.isApplicable(book, now)) {
        price = policy.apply(price);
      }
    }
    return price;
  }
}

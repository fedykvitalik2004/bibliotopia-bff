package org.vitalii.fedyk.bibliotopiabff.domain.book.policy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.utils.BlackFridayUtils;

public class BlackFridayPricePolicy implements PricePolicy {
  private static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.8);

  @Override
  public boolean isApplicable(final Book book, final ZonedDateTime now) {
    final LocalDate blackFriday = BlackFridayUtils.calculateFromYear(now.getYear());

    final ZonedDateTime from = blackFriday.atTime(LocalTime.of(0, 0)).atZone(now.getZone());

    final ZonedDateTime to = blackFriday.plusDays(2).atTime(LocalTime.MAX).atZone(now.getZone());

    return !now.isBefore(from) && !now.isAfter(to);
  }

  @Override
  public BigDecimal apply(final BigDecimal price) {
    return price.multiply(DISCOUNT);
  }
}

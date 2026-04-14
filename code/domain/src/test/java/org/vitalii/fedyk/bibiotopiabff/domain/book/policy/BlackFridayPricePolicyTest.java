package org.vitalii.fedyk.bibiotopiabff.domain.book.policy;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vitalii.fedyk.bibliotopiabff.domain.book.policy.BlackFridayPricePolicy;

class BlackFridayPricePolicyTest {
  private final BlackFridayPricePolicy policy = new BlackFridayPricePolicy();

  @ParameterizedTest
  @MethodSource("provideDatesWithinBlackFriday")
  void should_returnTrue_whenDateIsWithinBlackFriday(final ZonedDateTime now) {
    assertTrue(policy.isApplicable(null, now));
  }

  @ParameterizedTest
  @MethodSource("provideDatesOutsideBlackFriday")
  void should_returnFalse_whenDateIsOutsideBlackFriday(ZonedDateTime now) {
    assertFalse(policy.isApplicable(null, now));
  }

  // ---------------- DATA PROVIDERS ----------------

  static Stream<Arguments> provideDatesWithinBlackFriday() {
    return Stream.of(
        Arguments.of(ZonedDateTime.parse("2026-11-27T00:00:00+02:00[Europe/Kyiv]")),
        Arguments.of(ZonedDateTime.parse("2026-11-27T15:30:00+02:00[Europe/Kyiv]")),
        Arguments.of(ZonedDateTime.parse("2026-11-28T12:00:00+02:00[Europe/Kyiv]")),
        Arguments.of(ZonedDateTime.parse("2026-11-29T23:59:59.999999999+02:00[Europe/Kyiv]")));
  }

  static Stream<Arguments> provideDatesOutsideBlackFriday() {
    return Stream.of(
        Arguments.of(ZonedDateTime.parse("2026-11-26T23:59:59.999999999+02:00[Europe/Kyiv]")),
        Arguments.of(ZonedDateTime.parse("2026-11-30T00:00:00+02:00[Europe/Kyiv]")),
        Arguments.of(ZonedDateTime.parse("2026-12-01T12:00:00+02:00[Europe/Kyiv]")));
  }

  @Test
  void should_ApplyTwentyPercentDiscount_when_PriceIsPositive() {
    final BigDecimal discounted = policy.apply(new BigDecimal("100.00"));

    assertEquals(new BigDecimal("80.000"), discounted);
  }
}

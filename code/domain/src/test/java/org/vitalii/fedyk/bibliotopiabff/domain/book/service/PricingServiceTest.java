package org.vitalii.fedyk.bibliotopiabff.domain.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;
import org.vitalii.fedyk.bibliotopiabff.domain.book.policy.PricePolicy;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {

  @Mock private PricePolicy activePolicy;

  @Mock private PricePolicy inactivePolicy;

  @Mock private Book book;

  private PricingService pricingService;

  @BeforeEach
  void setUp() {
    this.pricingService = new PricingService(List.of(this.inactivePolicy, this.activePolicy));
  }

  @Test
  void shouldCalculatePriceApplyingOnlyApplicablePolicies() {
    // Given
    final var initialPrice = new BigDecimal("100.00");
    final var discountedPrice = new BigDecimal("85.00");
    final var zoneId = ZoneId.of("Europe/Kyiv");

    when(this.book.getPrice()).thenReturn(initialPrice);

    when(this.inactivePolicy.isApplicable(any(), any())).thenReturn(false);

    when(this.activePolicy.isApplicable(eq(book), any())).thenReturn(true);
    when(this.activePolicy.apply(initialPrice)).thenReturn(discountedPrice);

    // When
    final var finalPrice = this.pricingService.calculatePrice(book, zoneId);

    // Then
    assertEquals(discountedPrice, finalPrice);

    verify(this.book).getPrice();

    verify(this.inactivePolicy).isApplicable(eq(book), any());
    verifyNoMoreInteractions(this.inactivePolicy);

    verify(this.activePolicy).isApplicable(eq(book), any());
    verify(this.activePolicy).apply(initialPrice);
  }

  @Test
  void shouldReturnOriginalPriceWhenNoPoliciesApplicable() {
    // Given
    final var initialPrice = new BigDecimal("50.00");

    when(this.book.getPrice()).thenReturn(initialPrice);
    when(this.inactivePolicy.isApplicable(any(), any())).thenReturn(false);
    when(this.activePolicy.isApplicable(any(), any())).thenReturn(false);

    // When
    final var finalPrice =
        this.pricingService.calculatePrice(this.book, ZoneId.of("America/New_York"));

    // Then
    assertEquals(initialPrice, finalPrice);
  }
}

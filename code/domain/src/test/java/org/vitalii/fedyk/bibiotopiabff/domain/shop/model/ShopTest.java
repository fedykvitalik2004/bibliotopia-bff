package org.vitalii.fedyk.bibiotopiabff.domain.shop.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.bibliotopiabff.domain.shop.model.Shop;

class ShopTest {
  @Test
  void should_ReturnTrue_when_CurrentInstantIsProvided() {
    final Shop shop =
        Shop.builder()
            .id(UUID.randomUUID())
            .name("Shop")
            .zoneId(ZoneId.of("Europe/Kyiv"))
            .dailyOpen(LocalTime.of(9, 0))
            .dailyClose(LocalTime.of(18, 0))
            .build();

    Instant instant = Instant.parse("2026-04-10T20:01:11.603814Z");

    assertTrue(shop.isOpenNow(instant));
  }
}

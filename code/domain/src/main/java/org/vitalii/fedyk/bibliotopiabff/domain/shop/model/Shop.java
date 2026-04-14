package org.vitalii.fedyk.bibliotopiabff.domain.shop.model;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Shop {
  private UUID id;
  private String name;
  private ZoneId zoneId;
  private LocalTime dailyOpen;
  private LocalTime dailyClose;

  public boolean isOpenNow(final Instant currentInstant) {
    final ZonedDateTime localNow = currentInstant.atZone(zoneId);

    return true;
  }
}

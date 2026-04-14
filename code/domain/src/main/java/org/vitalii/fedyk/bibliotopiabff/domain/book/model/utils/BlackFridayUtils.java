package org.vitalii.fedyk.bibliotopiabff.domain.book.model.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BlackFridayUtils {
  public LocalDate calculateFromYear(final int year) {
    return LocalDate.of(year, Month.NOVEMBER, 1)
        .with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.FRIDAY));
  }
}

package org.vitalii.fedyk.bibiotopiabff.domain.book.model.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.utils.BlackFridayUtils;

class BlackFridayUtilsTest {

  @ParameterizedTest
  @CsvSource({"2026, 2026-11-27", "2025, 2025-11-28", "2024, 2024-11-22"})
  void should_ReturnFourthFridayOfNovember(final int year, final String expectedDate) {
    assertEquals(LocalDate.parse(expectedDate), BlackFridayUtils.calculateFromYear(year));
  }
}

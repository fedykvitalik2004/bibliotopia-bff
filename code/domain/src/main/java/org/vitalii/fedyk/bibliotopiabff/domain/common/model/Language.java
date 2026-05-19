package org.vitalii.fedyk.bibliotopiabff.domain.common.model;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Language {
  UK("uk"),
  HE("he"),
  EN("en");

  private final String value;

  public static Language fromCode(final String code) {
    return Arrays.stream(Language.values())
        .filter(lang -> lang.getValue().equalsIgnoreCase(code.trim()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unsupported language code: " + code));
  }
}

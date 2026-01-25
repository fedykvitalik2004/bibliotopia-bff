package org.vitalii.fedyk;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Language {
  ENGLISH(0),
  HEBREW(1),
  UKRAINIAN(2);

  private final int code;

  public static Language fromCode(int code) {
    for(Language language: Language.values()) {
      if (language.code == code) {
        return language;
      }
    }
    return null;
  }
}
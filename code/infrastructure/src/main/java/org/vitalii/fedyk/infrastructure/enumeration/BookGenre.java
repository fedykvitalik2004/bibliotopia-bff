package org.vitalii.fedyk.infrastructure.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookGenre {
  ICTION(0),
  NON_FICTION(1),
  MYSTERY(2),
  FANTASY(3),
  SCIENCE(4),
  HISTORY(5),
  ROMANCE(6),
  THRILLER(7),
  HORROR(8),
  BIOGRAPHY(9),
  SELF_HELP(10),
  POETRY(11),
  CLASSICS(12),
  ADVENTURE(13),
  CHILDRENS(14),
  COMICS(15),
  GRAPHIC_NOVEL(16),
  PHILOSOPHY(17),
  RELIGION(18),
  TRAVEL(19),
  COOKING(20);

  private final int code;

  public static BookGenre fromCode(int code) {
    for (BookGenre genre : BookGenre.values()) {
      if (genre.getCode() == code) {
        return genre;
      }
    }
    return null;
  }

  
}

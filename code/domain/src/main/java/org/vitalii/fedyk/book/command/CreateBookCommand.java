package org.vitalii.fedyk.book.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vitalii.fedyk.book.BookGenre;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookCommand {
  private String isbn;
  private String title;
  private List<UUID> authorIds;
  private int pageCount;
  private String coverImageUrl;
  private BookGenre bookGenre;
}

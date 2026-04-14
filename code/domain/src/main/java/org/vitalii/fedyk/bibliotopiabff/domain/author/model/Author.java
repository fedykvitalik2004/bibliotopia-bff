package org.vitalii.fedyk.bibliotopiabff.domain.author.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Author {
  private Long id;
  private LocalDate birthDate;
  private LocalDate deathDate;
  private List<AuthorTranslation> translations;
}

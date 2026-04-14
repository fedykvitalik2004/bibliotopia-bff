package org.vitalii.fedyk.bibliotopiabff.domain.author.model;

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
public class AuthorTranslation {
  private Long id;
  private String firstName;
  private String lastName;
  private String description;
  private String language;
}

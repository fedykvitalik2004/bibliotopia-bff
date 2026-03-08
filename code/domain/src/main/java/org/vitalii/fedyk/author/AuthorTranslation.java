package org.vitalii.fedyk.author;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vitalii.fedyk.common.enumeration.Language;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorTranslation {
  private UUID id;

  private OffsetDateTime createdAt;

  private String firstName;

  private String lastName;

  private String description;

  private Language language;
}

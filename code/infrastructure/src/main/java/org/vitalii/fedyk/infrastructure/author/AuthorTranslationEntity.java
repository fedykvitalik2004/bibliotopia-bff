package org.vitalii.fedyk.infrastructure.author;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.vitalii.fedyk.infrastructure.BaseEntity;
import org.vitalii.fedyk.infrastructure.enumeration.Language;
import org.vitalii.fedyk.infrastructure.enumeration.converter.LanguageConverter;

@Entity
@Table(name = "author_translations")
public class AuthorTranslation extends BaseEntity {
  private String firstName;

  private String lastName;

  private String description;

  @ManyToOne
  private Author author;

  @Convert(converter = LanguageConverter.class)
  private Language language;
}

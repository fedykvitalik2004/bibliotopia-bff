package org.vitalii.fedyk.infrastructure;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.vitalii.fedyk.Language;

@Converter
public class LanguageConverter implements AttributeConverter<Language, Integer> {
  @Override
  public Integer convertToDatabaseColumn(final Language attribute) {
    return attribute != null ? attribute.getCode() : null;
  }

  @Override
  public Language convertToEntityAttribute(final Integer dbData) {
    return dbData != null ? Language.fromCode(dbData) : null;
  }
}

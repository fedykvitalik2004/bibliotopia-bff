package org.vitalii.fedyk.bibliotopiabff.domain.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessageConstants {
  public static final String ISBN_MANDATORY = "{error.book.isbn.mandatory}";
  public static final String PRICE_MANDATORY = "{error.book.price.mandatory}";
  public static final String PRICE_POSITIVE = "{error.book.price.positive}";
  public static final String TRANSLATIONS_REQUIRED = "{error.book.translations.required}";
  public static final String GENRES_REQUIRED = "{error.book.genres.required}";
  public static final String AUTHOR_IDS_REQUIRED = "{error.book.authorIds.required}";
  public static final String TITLE_MANDATORY = "{error.book.title.mandatory}";
  public static final String DESCRIPTION_MANDATORY = "{error.book.description.mandatory}";
  public static final String LANGUAGE_MANDATORY = "{error.book.language.mandatory}";
  public static final String GENRE_INVALID = "{error.book.genre.invalid}";
  public static final String AUTHOR_ID_INVALID = "{error.book.authorId.invalid}";

  public static final String INVALID_DATE_RANGE = "{error.author.date.range.invalid}";
  public static final String BIRTH_DATE_REQUIRED = "{error.author.birthDate.required}";
  public static final String BIRTH_DATE_PAST = "{error.author.birthDate.past}";
  public static final String FIRST_NAME_MANDATORY =
      "{error.author.translation.firstName.mandatory}";
  public static final String FIRST_NAME_SIZE = "{error.author.translation.firstName.size}";
  public static final String LAST_NAME_MANDATORY = "{error.author.translation.lastName.mandatory}";
  public static final String LAST_NAME_SIZE = "{error.author.translation.lastName.size}";
}

package org.vitalii.fedyk.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.vitalii.fedyk.author.controller.dto.AuthorDto;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, AuthorDto> {
  @Override
  public boolean isValid(final AuthorDto value, final ConstraintValidatorContext context) {
    if (value.getBirthDate() == null || value.getDeathDate() == null) {
      return true;
    }

    final boolean isBirthDateBeforeDeathDate = value.getBirthDate().isBefore(value.getDeathDate());
    if (!isBirthDateBeforeDeathDate) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Birth date can't be after death date")
              .addPropertyNode("birthDate")
              .addConstraintViolation();

    }
    return isBirthDateBeforeDeathDate;
  }
}

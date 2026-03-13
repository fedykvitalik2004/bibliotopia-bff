package org.vitalii.fedyk.annotation.validation;

import static org.vitalii.fedyk.localization.ValidationMessageConstants.INVALID_DATE_RANGE;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.vitalii.fedyk.author.dto.AuthorRequest;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, AuthorRequest> {
  @Override
  public boolean isValid(final AuthorRequest value, final ConstraintValidatorContext context) {
    if (value.birthDate() == null || value.deathDate() == null) {
      return true;
    }

    final boolean isBirthDateBeforeDeathDate = value.birthDate().isBefore(value.deathDate());
    if (!isBirthDateBeforeDeathDate) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(INVALID_DATE_RANGE)
          .addPropertyNode("deathDate")
          .addConstraintViolation();
    }
    return isBirthDateBeforeDeathDate;
  }
}

package org.vitalii.fedyk.bibliotopiabff.domain.user.exception;

import lombok.Getter;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;

@Getter
public class AccountProviderConflictException extends RuntimeException {
  private final User.AuthProvider provider;

  public AccountProviderConflictException(final String message, final User.AuthProvider provider) {
    super(message);
    this.provider = provider;
  }
}

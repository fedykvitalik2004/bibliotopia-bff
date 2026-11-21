package org.vitalii.fedyk.application.user.port.out;

import org.vitalii.fedyk.domain.user.model.EncodedPassword;

public interface PasswordEncoder {
  EncodedPassword encode(String rawPassword);

  boolean matches(String rawPassword, String hashedPassword);
}

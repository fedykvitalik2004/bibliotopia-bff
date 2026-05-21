package org.vitalii.fedyk.bibliotopiabff.application.user.port.out;

import org.vitalii.fedyk.bibliotopiabff.domain.user.model.EncodedPassword;

public interface PasswordEncoder {
  EncodedPassword encode(String rawPassword);

  boolean matches(String rawPassword, String hashedPassword);
}

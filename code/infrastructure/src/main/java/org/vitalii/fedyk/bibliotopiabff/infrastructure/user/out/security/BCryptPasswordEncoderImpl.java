package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.PasswordEncoder;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.EncodedPassword;

@Component
public class BCryptPasswordEncoderImpl implements PasswordEncoder {
  private final BCryptPasswordEncoder bCryptPasswordHasher = new BCryptPasswordEncoder(15);

  @Override
  public EncodedPassword encode(final String rawPassword) {
    return new EncodedPassword(this.bCryptPasswordHasher.encode(rawPassword));
  }

  @Override
  public boolean matches(final String rawPassword, final String hashedPassword) {
    return this.bCryptPasswordHasher.matches(rawPassword, hashedPassword);
  }
}

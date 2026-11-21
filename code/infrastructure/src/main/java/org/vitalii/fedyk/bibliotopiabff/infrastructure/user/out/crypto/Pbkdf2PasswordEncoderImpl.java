package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.crypto;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.PasswordEncoder;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.EncodedPassword;

@Component
public class Pbkdf2PasswordEncoderImpl implements PasswordEncoder {
  private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
  private static final String FORMAT_PREFIX = "pbkdf2-sha256";
  private static final int ITERATIONS = 210_000;
  private static final int SALT_LENGTH_BYTES = 16;
  private static final int KEY_LENGTH_BITS = 256;

  private final SecureRandom secureRandom = new SecureRandom();

  @Override
  public EncodedPassword encode(final String rawPassword) {
    final byte[] salt = new byte[SALT_LENGTH_BYTES];
    this.secureRandom.nextBytes(salt);
    final byte[] hash = this.derive(rawPassword, salt, ITERATIONS);

    return new EncodedPassword(
        FORMAT_PREFIX
            + "$"
            + ITERATIONS
            + "$"
            + Base64.getEncoder().encodeToString(salt)
            + "$"
            + Base64.getEncoder().encodeToString(hash));
  }

  @Override
  public boolean matches(final String rawPassword, final String encodedPassword) {
    final String[] parts = encodedPassword.split("\\$", -1);
    if (parts.length != 4 || !FORMAT_PREFIX.equals(parts[0])) {
      return false;
    }

    try {
      final int iterations = Integer.parseInt(parts[1]);
      final byte[] salt = Base64.getDecoder().decode(parts[2]);
      final byte[] expectedHash = Base64.getDecoder().decode(parts[3]);
      final byte[] actualHash = this.derive(rawPassword, salt, iterations);
      return MessageDigest.isEqual(expectedHash, actualHash);
    } catch (IllegalArgumentException exception) {
      return false;
    }
  }

  private byte[] derive(final String password, final byte[] salt, final int iterations) {
    final PBEKeySpec keySpec =
        new PBEKeySpec(password.toCharArray(), salt, iterations, KEY_LENGTH_BITS);
    try {
      return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(keySpec).getEncoded();
    } catch (GeneralSecurityException exception) {
      throw new IllegalStateException("Could not derive password hash", exception);
    } finally {
      keySpec.clearPassword();
    }
  }
}

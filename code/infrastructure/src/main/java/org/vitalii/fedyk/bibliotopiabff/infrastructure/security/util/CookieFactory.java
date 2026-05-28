package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.util;

import static org.vitalii.fedyk.bibliotopiabff.infrastructure.security.SecurityConstants.ACCESS_TOKEN_COOKIE_NAME;
import static org.vitalii.fedyk.bibliotopiabff.infrastructure.security.SecurityConstants.REFRESH_TOKEN_COOKIE_NAME;

import java.time.Duration;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseCookie;

@UtilityClass
public class CookieFactory {
  /** Creates a short-lived cookie for the Access Token. */
  public ResponseCookie createAccessCookie(final String token, final Duration duration) {
    return buildCookie(ACCESS_TOKEN_COOKIE_NAME, token, duration);
  }

  /** Creates a long-lived cookie for the Refresh Token. */
  public ResponseCookie createRefreshCookie(final String token, final Duration duration) {
    return buildCookie(REFRESH_TOKEN_COOKIE_NAME, token, duration);
  }

  public ResponseCookie createDeleteAccessCookie() {
    return createDeleteCookie(ACCESS_TOKEN_COOKIE_NAME);
  }

  public ResponseCookie createDeleteRefreshCookie() {
    return createDeleteCookie(REFRESH_TOKEN_COOKIE_NAME);
  }

  private ResponseCookie createDeleteCookie(final String cookieName) {
    return ResponseCookie.from(cookieName, "")
        .httpOnly(true)
        .secure(true)
        .path("/")
        .sameSite("Strict")
        .maxAge(0)
        .build();
  }

  /** Core private builder keeping the cookie configuration strictly uniform. */
  private ResponseCookie buildCookie(
      final String name, final String value, final Duration duration) {
    return ResponseCookie.from(name, value)
        .httpOnly(true)
        .secure(true)
        .sameSite("Lax")
        .path("/")
        .maxAge(duration)
        .build();
  }
}

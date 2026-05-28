package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.extractor;

import java.util.Optional;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class GoogleUserInfoExtractor implements OAuthUserInfoExtractor {
  @Override
  public String getProviderName() {
    return "google";
  }

  @Override
  public Optional<String> extractEmail(final OAuth2User user) {
    return Optional.ofNullable(user.getAttribute("email"));
  }

  @Override
  public Optional<String> extractFirstName(final OAuth2User user) {
    return Optional.ofNullable(user.getAttribute("given_name"));
  }
}

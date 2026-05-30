package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.extractor;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class GoogleUserInfoExtractor implements OAuthUserInfoExtractor {
  @Override
  public String getProviderName() {
    return "google";
  }

  @Override
  public String extractEmail(final OAuth2User user) {
    return user.getAttribute("email");
  }

  @Override
  public String extractFirstName(final OAuth2User user) {
    return user.getAttribute("given_name");
  }
}

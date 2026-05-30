package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.extractor;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthUserInfoExtractor {
  String getProviderName();

  String extractEmail(OAuth2User user);

  String extractFirstName(OAuth2User user);
}

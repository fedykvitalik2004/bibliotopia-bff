package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.extractor;

import java.util.Optional;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthUserInfoExtractor {
  String getProviderName();

  Optional<String> extractEmail(OAuth2User user);

  Optional<String> extractFirstName(OAuth2User user);
}

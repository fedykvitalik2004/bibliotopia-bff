package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.extractor;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OAuthExtractorRegistry {
  private Map<String, OAuthUserInfoExtractor> extractors;

  public OAuthExtractorRegistry(final List<OAuthUserInfoExtractor> extractorList) {
    this.extractors =
        extractorList.stream()
            .collect(toMap(e -> e.getProviderName().toLowerCase(), extractor -> extractor));
  }

  public OAuthUserInfoExtractor getExtractor(final String provider) {
    return this.extractors.get(provider.toLowerCase());
  }
}

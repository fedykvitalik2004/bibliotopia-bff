package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtProperties {
  private String secretKey;

  private long accessExpiration;

  private long refreshExpiration;
}

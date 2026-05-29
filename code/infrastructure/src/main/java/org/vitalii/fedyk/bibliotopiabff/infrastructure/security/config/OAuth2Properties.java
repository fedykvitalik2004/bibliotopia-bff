package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application.security.oauth2")
public class OAuth2Properties {
  private String redirectUrl;
}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vitalii.fedyk.bibliotopiabff.domain.book.policy.BlackFridayPricePolicy;
import org.vitalii.fedyk.bibliotopiabff.domain.book.policy.PricePolicy;
import org.vitalii.fedyk.bibliotopiabff.domain.book.service.PricingService;

@Configuration
public class PricingConfig {
  @Bean
  public PricePolicy blackFridayPolicy() {
    return new BlackFridayPricePolicy();
  }

  @Bean
  public PricingService pricingService(final List<PricePolicy> allPolicies) {
    return new PricingService(allPolicies);
  }
}

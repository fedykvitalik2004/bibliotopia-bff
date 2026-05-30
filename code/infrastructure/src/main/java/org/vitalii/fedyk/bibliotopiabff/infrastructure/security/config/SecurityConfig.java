package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.filter.JwtAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private final CustomSuccessHandler customSuccessHandler;

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean //todo: improve here
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) {
    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/error",
                        "/oauth2/**",
                        "/login/**",
                        "/api/v1/auth/me",
                        "/api/v1/auth/refresh")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .oauth2Login(oauth -> oauth.successHandler(this.customSuccessHandler))
        .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}

package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.UserView;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.in.ResolveExternalUserUseCase;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.extractor.OAuthExtractorRegistry;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.extractor.OAuthUserInfoExtractor;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.provider.JwtProvider;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.util.CookieFactory;

@Component
@AllArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
  private final ResolveExternalUserUseCase resolveExternalUserUseCase;

  private final OAuthExtractorRegistry extractorRegistry;

  private final JwtProvider jwtProvider;

  private final OAuth2Properties oAuth2Properties;

  @Override
  public void onAuthenticationSuccess(
      final @NonNull HttpServletRequest request,
      final @NonNull HttpServletResponse response,
      final Authentication authentication)
      throws IOException {
    final OAuth2User user = (OAuth2User) authentication.getPrincipal();
    final OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
    final String provider = oauthToken.getAuthorizedClientRegistrationId();

    final OAuthUserInfoExtractor extractor = this.extractorRegistry.getExtractor(provider);

    // Create a user
    final ResolveExternalUserUseCase.ResolveExternalUserCommand command =
        this.createCommand(extractor, user);
    final UserView userView = this.resolveExternalUserUseCase.resolve(command);

    this.issueAuthCookies(response, userView);

    response.sendRedirect(this.oAuth2Properties.getRedirectUrl());
  }

  private void issueAuthCookies(final HttpServletResponse response, final UserView userView) {
    final Long id = userView.id();

    final String accessToken =
        this.jwtProvider.generateAccessToken(id, userView.roles(), userView.permissions());
    final String refreshToken = this.jwtProvider.generateRefreshToken(id);

    final ResponseCookie accessCookie =
        CookieFactory.createAccessCookie(accessToken, Duration.ofMinutes(15));
    final ResponseCookie refreshCookie =
        CookieFactory.createRefreshCookie(refreshToken, Duration.ofDays(7));

    response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
  }

  private ResolveExternalUserUseCase.ResolveExternalUserCommand createCommand(
      final OAuthUserInfoExtractor extractor, final OAuth2User user) {
    return ResolveExternalUserUseCase.ResolveExternalUserCommand.builder()
        .email(extractor.extractEmail(user))
        .firstName(extractor.extractFirstName(user))
        .providerId(extractor.getProviderName())
        .build();
  }
}

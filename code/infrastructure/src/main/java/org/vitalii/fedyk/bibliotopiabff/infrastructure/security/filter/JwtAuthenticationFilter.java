package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.filter;

import static org.vitalii.fedyk.bibliotopiabff.infrastructure.security.SecurityConstants.ACCESS_TOKEN_COOKIE_NAME;
import static org.vitalii.fedyk.bibliotopiabff.infrastructure.security.SecurityConstants.ROLE_PREFIX;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.provider.JwtProvider;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain)
      throws ServletException, IOException {
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      this.extractTokenFromCookie(request)
          .filter(this.jwtProvider::isTokenValid)
          .ifPresent(
              accessToken -> {
                final long userId = this.jwtProvider.extractUserId(accessToken);
                final List<String> roles = this.jwtProvider.extractRoles(accessToken);
                final List<String> permissions = this.jwtProvider.extractPermissions(accessToken);

                final Stream<SimpleGrantedAuthority> roleAuthorities =
                    roles.stream()
                        .map(roleName -> new SimpleGrantedAuthority(ROLE_PREFIX + roleName));
                final Stream<SimpleGrantedAuthority> permissionAuthorities =
                    permissions.stream().map(SimpleGrantedAuthority::new);
                final List<SimpleGrantedAuthority> authorities =
                    Stream.concat(roleAuthorities, permissionAuthorities).toList();

                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);
                usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);
              });
    }
    filterChain.doFilter(request, response);
  }

  private Optional<String> extractTokenFromCookie(final HttpServletRequest request) {
    if (request.getCookies() == null) {
      return Optional.empty();
    }

    return Arrays.stream(request.getCookies())
        .filter(cookie -> ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName()))
        .map(Cookie::getValue)
        .findFirst();
  }
}

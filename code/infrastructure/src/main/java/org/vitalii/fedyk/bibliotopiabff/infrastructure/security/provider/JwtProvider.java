package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.security.config.JwtProperties;

@Component
public class JwtProvider {
  private final SecretKey secretKey;
  private final long accessExpirationInMs;
  private final long refreshExpirationInMs;
  private static final String ROLES_CLAIM = "roles";
  private static final String PERMISSIONS_CLAIM = "permissions";

  public JwtProvider(final JwtProperties jwtProperties) {
    this.secretKey =
        Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    this.accessExpirationInMs = jwtProperties.getAccessExpiration();
    this.refreshExpirationInMs = jwtProperties.getRefreshExpiration();
  }

  public String generateAccessToken(
      final Long id, Set<String> roleNames, Set<String> permissionNames) {
    final Map<String, Object> claims = new HashMap<>();
    claims.put(ROLES_CLAIM, roleNames);
    claims.put(PERMISSIONS_CLAIM, permissionNames);

    return buildToken(claims, String.valueOf(id), this.accessExpirationInMs);
  }

  public String generateRefreshToken(final Long id) {
    return buildToken(new HashMap<>(), String.valueOf(id), this.refreshExpirationInMs);
  }

  private String buildToken(
      final Map<String, Object> claims, final String subject, final long expiration) {
    final long currentTimeMillis = System.currentTimeMillis();
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(new Date(currentTimeMillis))
        .expiration(new Date(currentTimeMillis + expiration))
        .signWith(secretKey)
        .compact();
  }

  public Long extractUserId(final String token) {
    final String subject = this.extractClaim(token, Claims::getSubject);
    return Long.valueOf(subject);
  }

  public boolean isTokenValid(final String token) {
    try {
      return !this.isTokenExpired(token);
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  public List<String> extractRoles(final String token) {
    return this.extractClaim(token, claims -> claims.get(ROLES_CLAIM, List.class));
  }

  @SuppressWarnings("unchecked")
  public List<String> extractPermissions(final String token) {
    return this.extractClaim(token, claims -> claims.get(PERMISSIONS_CLAIM, List.class));
  }

  private boolean isTokenExpired(final String token) {
    return this.extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(final String token) {
    return this.extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
    final Claims claims = this.extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(final String token) {
    return Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token).getPayload();
  }
}

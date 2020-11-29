package lpfx.desafio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTUtils {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  String generateToken(final UserDetails user) {
    final List<String> authorities = user.getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toList());

    return Jwts.builder()
      .setSubject(user.getUsername())
      .claim("ROLES", authorities)
      .setExpiration(new Date(System.currentTimeMillis() + expiration))
      .signWith(SignatureAlgorithm.HS512, secret.getBytes())
      .compact();
  }

  boolean isTokenValid(final String token) {
    Claims claims = getClaims(token);
    if (claims != null) {
      final String user = claims.getSubject();
      final Date expirationDate = claims.getExpiration();
      final Date now = new Date(System.currentTimeMillis());
      return user != null && now.before(expirationDate);
    }
    return false;
  }

  String getUsername(final String token) {
    final Claims claims = getClaims(token);
    if (claims != null) {
      return claims.getSubject();
    }
    return null;
  }

  private Claims getClaims(final String token) {
      return Jwts
        .parser()
        .setSigningKey(secret.getBytes())
        .parseClaimsJws(token)
        .getBody();
  }
}

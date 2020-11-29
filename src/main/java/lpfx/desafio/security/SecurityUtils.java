package lpfx.desafio.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

  /**
   * <h1>getCurrentUserLogin</h1>
   * <h3>Pega o usuario logado</h3>
   * @return login do usuario logado
   */
  public static Optional<String> getCurrentUserLogin() {
    final SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
      .map(authentication -> {
        if (authentication.getPrincipal() instanceof UserDetails) {
          UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
          return springSecurityUser.getUsername();
        }
        if (authentication.getPrincipal() instanceof String) {
          return (String) authentication.getPrincipal();
        }
        return null;
      });
  }
}

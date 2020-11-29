package lpfx.desafio.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lpfx.desafio.model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;
  private final JWTUtils jwtUtil;

  @Override
  public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res) {
    try {
      final Usuario usuario = new ObjectMapper().readValue(req.getInputStream(), Usuario.class);
      final UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha());

      return authenticationManager.authenticate(token);
    } catch (IOException e) {
      throw new RuntimeException("Unable to get json from request", e);
    }
  }

  @Override
  protected void successfulAuthentication(final HttpServletRequest req,
                                     final HttpServletResponse res,
                                     final FilterChain filterChain,
                                     final Authentication auth) {
    final UserDetailsImpl user = ((UserDetailsImpl) auth.getPrincipal());
    final String token = jwtUtil.generateToken(user);
    res.addHeader("Authorization", "Bearer " + token);
    res.addHeader("Access-Control-Expose-Headers", "Authorization");
  }

  private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException {
      response.setStatus(401);
      response.setContentType("application/json");
      response.getWriter().append(json());
    }
  }

  private String json() {
    long date = new Date().getTime();
    return "{\"timestamp\": " + date + ", "
      + "\"status\": 401, "
      + "\"error\": \"Nao autorizado\", "
      + "\"message\": \"Email ou senha invalidos\", "
      + "\"path\": \"/login\"}";
  }
}

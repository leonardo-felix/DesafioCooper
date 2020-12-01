package lpfx.desafio.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  private final JWTUtils jwtUtil;
  private final UserDetailsService userDetailsService;

  public JWTAuthorizationFilter(
    final AuthenticationManager authenticationManager,
    final JWTUtils jwtUtil,
    final UserDetailsService userDetailsService) {
    super(authenticationManager);
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest req,
                                  final HttpServletResponse res,
                                  final FilterChain filterChain) throws IOException, ServletException {
    final String header = req.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
      if (auth != null) {
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }
    filterChain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(final String token) {
    if (jwtUtil.isTokenValid(token)) {
      final String username = jwtUtil.getUsername(token);
      final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    return null;
  }
}

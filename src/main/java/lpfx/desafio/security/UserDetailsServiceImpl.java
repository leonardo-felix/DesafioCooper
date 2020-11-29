package lpfx.desafio.security;

import lombok.AllArgsConstructor;
import lpfx.desafio.model.Usuario;
import lpfx.desafio.repository.UsuarioRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Primary
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(final String login) {
    final Usuario usuario = usuarioRepository
            .findByLogin(login)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario " + login + " nao encontrado"));
    return new UserDetailsImpl(usuario);
  }
}

package lpfx.desafio.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lpfx.desafio.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1704306946201419444L;

  @Getter
  private final Integer id;

  private final String login;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Usuario usuario) {
    this.id = Math.toIntExact(usuario.getId());
    this.login = usuario.getLogin();
    this.password = usuario.getSenha();

    final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getUsuarioPapel().getNome());
    this.authorities = Collections.unmodifiableCollection(Collections.singleton(authority));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return login;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

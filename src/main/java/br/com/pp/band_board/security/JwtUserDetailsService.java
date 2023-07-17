package br.com.pp.band_board.security;

import br.com.pp.band_board.usuario.Usuario;
import br.com.pp.band_board.usuario.UsuarioRepository;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public JwtUserDetailsService(final UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public JwtUserDetails loadUserByUsername(final String username) {
        final Usuario usuario = usuarioRepository.findByNomeIgnoreCase(username);
        if (usuario == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        final List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority(UserRoles.ROLE_USER));
        return new JwtUserDetails(usuario.getIdUser(), username, usuario.getSenha(), roles);
    }

}

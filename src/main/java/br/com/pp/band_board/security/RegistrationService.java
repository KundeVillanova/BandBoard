package br.com.pp.band_board.security;

import br.com.pp.band_board.usuario.Usuario;
import br.com.pp.band_board.usuario.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RegistrationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(final UsuarioRepository usuarioRepository,
            final PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean nomeExists(final RegistrationRequest registrationRequest) {
        return usuarioRepository.existsByNomeIgnoreCase(registrationRequest.getNome());
    }

    public void register(final RegistrationRequest registrationRequest) {
        log.info("registering new user: {}", registrationRequest.getNome());

        final Usuario usuario = new Usuario();
        usuario.setNome(registrationRequest.getNome());
        usuario.setEmail(registrationRequest.getEmail());
        usuario.setDataNascimento(registrationRequest.getDataNascimento());
        usuario.setCelular(registrationRequest.getCelular());
        usuario.setSenha(passwordEncoder.encode(registrationRequest.getPassword()));
        usuarioRepository.save(usuario);
    }

}

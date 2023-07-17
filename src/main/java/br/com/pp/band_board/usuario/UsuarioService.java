package br.com.pp.band_board.usuario;

import br.com.pp.band_board.nivel.Nivel;
import br.com.pp.band_board.nivel.NivelRepository;
import br.com.pp.band_board.post_banda.PostBanda;
import br.com.pp.band_board.post_banda.PostBandaRepository;
import br.com.pp.band_board.post_show.PostShow;
import br.com.pp.band_board.post_show.PostShowRepository;
import br.com.pp.band_board.tipo_musical.TipoMusical;
import br.com.pp.band_board.tipo_musical.TipoMusicalRepository;
import br.com.pp.band_board.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TipoMusicalRepository tipoMusicalRepository;
    private final PostBandaRepository postBandaRepository;
    private final PostShowRepository postShowRepository;
    private final NivelRepository nivelRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final TipoMusicalRepository tipoMusicalRepository,
            final PostBandaRepository postBandaRepository,
            final PostShowRepository postShowRepository, final NivelRepository nivelRepository,
            final PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.tipoMusicalRepository = tipoMusicalRepository;
        this.postBandaRepository = postBandaRepository;
        this.postShowRepository = postShowRepository;
        this.nivelRepository = nivelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("idUser"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Long idUser) {
        return usuarioRepository.findById(idUser)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getIdUser();
    }

    public void update(final Long idUser, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(idUser)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Long idUser) {
        usuarioRepository.deleteById(idUser);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setIdUser(usuario.getIdUser());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setDataNascimento(usuario.getDataNascimento());
        usuarioDTO.setCelular(usuario.getCelular());
        usuarioDTO.setTiposMusicais(usuario.getTiposMusicais().stream()
                .map(tipoMusical -> tipoMusical.getId())
                .collect(Collectors.toList()));
        usuarioDTO.setBandas(usuario.getBandas().stream()
                .map(postBanda -> postBanda.getIdBanda())
                .toList());
        usuarioDTO.setShows(usuario.getShows().stream()
                .map(postShow -> postShow.getIdShow())
                .toList());
        usuarioDTO.setExperiencias(usuario.getExperiencias().stream()
                .map(nivel -> nivel.getIdNivel())
                .toList());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        usuario.setCelular(usuarioDTO.getCelular());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        final List<TipoMusical> tiposMusicais = tipoMusicalRepository.findAllById(usuarioDTO.getTiposMusicais() == null ?
                Collections.emptyList() : usuarioDTO.getTiposMusicais());
        usuario.setTiposMusicais(new HashSet<>(tiposMusicais));
        final List<PostBanda> bandas = postBandaRepository.findAllById(
                usuarioDTO.getBandas() == null ? Collections.emptyList() : usuarioDTO.getBandas());
        if (bandas.size() != (usuarioDTO.getBandas() == null ? 0 : usuarioDTO.getBandas().size())) {
            throw new NotFoundException("one of bandas not found");
        }
        usuario.setBandas(bandas.stream().collect(Collectors.toSet()));
        final List<PostShow> shows = postShowRepository.findAllById(
                usuarioDTO.getShows() == null ? Collections.emptyList() : usuarioDTO.getShows());
        if (shows.size() != (usuarioDTO.getShows() == null ? 0 : usuarioDTO.getShows().size())) {
            throw new NotFoundException("one of shows not found");
        }
        usuario.setShows(shows.stream().collect(Collectors.toSet()));
        final List<Nivel> experiencias = nivelRepository.findAllById(
                usuarioDTO.getExperiencias() == null ? Collections.emptyList() : usuarioDTO.getExperiencias());
        if (experiencias.size() != (usuarioDTO.getExperiencias() == null ? 0 : usuarioDTO.getExperiencias().size())) {
            throw new NotFoundException("one of experiencias not found");
        }
        usuario.setExperiencias(experiencias.stream().collect(Collectors.toSet()));
        return usuario;
    }

    public UsuarioDTO getByNome(String nome) {
        Usuario usuario = usuarioRepository.findByNomeIgnoreCase(nome);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }
        return mapToDTO(usuario, new UsuarioDTO());
    }

    public void adicionarTiposMusicais(Long idUser, List<Long> tiposMusicaisIds) {
        Usuario usuario = usuarioRepository.findById(idUser)
                .orElseThrow(NotFoundException::new);
        List<TipoMusical> tiposMusicais = tipoMusicalRepository.findAllById(tiposMusicaisIds);
        usuario.getTiposMusicais().addAll(tiposMusicais);
        usuarioRepository.save(usuario);
    }

    public void adicionarNiveisInstrumentos(Long idUser, List<Long> niveisIds) {
        Usuario usuario = usuarioRepository.findById(idUser)
                .orElseThrow(NotFoundException::new);
        List<Nivel> niveis = nivelRepository.findAllById(niveisIds);
        usuario.getExperiencias().addAll(niveis);
        usuarioRepository.save(usuario);
    }


}

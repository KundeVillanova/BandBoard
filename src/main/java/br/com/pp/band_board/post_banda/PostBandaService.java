package br.com.pp.band_board.post_banda;

import br.com.pp.band_board.nivel.Nivel;
import br.com.pp.band_board.nivel.NivelRepository;
import br.com.pp.band_board.tipo_musical.TipoMusical;
import br.com.pp.band_board.tipo_musical.TipoMusicalRepository;
import br.com.pp.band_board.usuario.Usuario;
import br.com.pp.band_board.usuario.UsuarioRepository;
import br.com.pp.band_board.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class PostBandaService {

    private final PostBandaRepository postBandaRepository;
    private final NivelRepository nivelRepository;
    private final TipoMusicalRepository tipoMusicalRepository;
    private final UsuarioRepository usuarioRepository;

    public PostBandaService(final PostBandaRepository postBandaRepository,
            final NivelRepository nivelRepository,
            final TipoMusicalRepository tipoMusicalRepository,
            final UsuarioRepository usuarioRepository) {
        this.postBandaRepository = postBandaRepository;
        this.nivelRepository = nivelRepository;
        this.tipoMusicalRepository = tipoMusicalRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<PostBandaDTO> findAll() {
        final List<PostBanda> postBandas = postBandaRepository.findAll(Sort.by("idBanda"));
        return postBandas.stream()
                .map(postBanda -> mapToDTO(postBanda, new PostBandaDTO()))
                .toList();
    }

    public PostBandaDTO get(final Long idBanda) {
        return postBandaRepository.findById(idBanda)
                .map(postBanda -> mapToDTO(postBanda, new PostBandaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PostBandaDTO postBandaDTO) {
        final PostBanda postBanda = new PostBanda();
        mapToEntity(postBandaDTO, postBanda);
        return postBandaRepository.save(postBanda).getIdBanda();
    }

    public void update(final Long idBanda, final PostBandaDTO postBandaDTO) {
        final PostBanda postBanda = postBandaRepository.findById(idBanda)
                .orElseThrow(NotFoundException::new);
        mapToEntity(postBandaDTO, postBanda);
        postBandaRepository.save(postBanda);
    }

    public void delete(final Long idBanda) {
        final PostBanda postBanda = postBandaRepository.findById(idBanda)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        usuarioRepository.findAllByBandas(postBanda)
                .forEach(usuario -> usuario.getBandas().remove(postBanda));
        postBandaRepository.delete(postBanda);
    }

    private PostBandaDTO mapToDTO(PostBanda postBanda, PostBandaDTO postBandaDTO) {
        postBandaDTO.setIdBanda(postBanda.getIdBanda());
        postBandaDTO.setTituloBanda(postBanda.getTituloBanda());
        postBandaDTO.setDescricao(postBanda.getDescricao());
        postBandaDTO.setVagas(postBanda.getVagas().stream()
                .map(nivel -> nivel.getIdNivel())
                .collect(Collectors.toList()));
        postBandaDTO.setIdTipoMusical(postBanda.getIdTipoMusical() == null ? null : postBanda.getIdTipoMusical().getId());
        return postBandaDTO;
    }

    private PostBanda mapToEntity(PostBandaDTO postBandaDTO, PostBanda postBanda) {
        postBanda.setTituloBanda(postBandaDTO.getTituloBanda());
        postBanda.setDescricao(postBandaDTO.getDescricao());
        List<Nivel> vagas = nivelRepository.findAllById(postBandaDTO.getVagas() != null ? postBandaDTO.getVagas() : Collections.emptyList());
        if (vagas.size() != (postBandaDTO.getVagas() != null ? postBandaDTO.getVagas().size() : 0)) {
            throw new NotFoundException("One or more vagas not found");
        }
        postBanda.setVagas(vagas.stream().collect(Collectors.toSet()));
        TipoMusical tipoMusical = postBandaDTO.getIdTipoMusical() != null ? tipoMusicalRepository.findById(postBandaDTO.getIdTipoMusical())
                .orElseThrow(() -> new NotFoundException("TipoMusical not found")) : null;
        postBanda.setIdTipoMusical(tipoMusical);
        Usuario usuario = usuarioRepository.findById(postBandaDTO.getIdUser())
                .orElseThrow(() -> new NotFoundException("User not found"));
        postBanda.setUsuario(usuario);
        return postBanda;
    }

}

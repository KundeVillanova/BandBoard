package br.com.pp.band_board.nivel;

import br.com.pp.band_board.instrumento.Instrumento;
import br.com.pp.band_board.instrumento.InstrumentoRepository;
import br.com.pp.band_board.post_banda.PostBandaRepository;
import br.com.pp.band_board.usuario.Usuario;
import br.com.pp.band_board.usuario.UsuarioRepository;
import br.com.pp.band_board.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class NivelService {

    private final NivelRepository nivelRepository;
    private final InstrumentoRepository instrumentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PostBandaRepository postBandaRepository;

    public NivelService(final NivelRepository nivelRepository,
            final InstrumentoRepository instrumentoRepository,
            final UsuarioRepository usuarioRepository,
            final PostBandaRepository postBandaRepository) {
        this.nivelRepository = nivelRepository;
        this.instrumentoRepository = instrumentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.postBandaRepository = postBandaRepository;
    }

    public List<NivelDTO> findAll() {
        final List<Nivel> nivels = nivelRepository.findAll(Sort.by("idNivel"));
        return nivels.stream()
                .map(nivel -> mapToDTO(nivel, new NivelDTO()))
                .toList();
    }

    public NivelDTO get(final Long idNivel) {
        return nivelRepository.findById(idNivel)
                .map(nivel -> mapToDTO(nivel, new NivelDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NivelDTO nivelDTO) {
        final Nivel nivel = new Nivel();
        mapToEntity(nivelDTO, nivel);
        return nivelRepository.save(nivel).getIdNivel();
    }

    public void update(final Long idNivel, final NivelDTO nivelDTO) {
        final Nivel nivel = nivelRepository.findById(idNivel)
                .orElseThrow(NotFoundException::new);
        mapToEntity(nivelDTO, nivel);
        nivelRepository.save(nivel);
    }

    public void delete(final Long idNivel) {
        final Nivel nivel = nivelRepository.findById(idNivel)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        usuarioRepository.findAllByExperiencias(nivel)
                .forEach(usuario -> usuario.getExperiencias().remove(nivel));
        postBandaRepository.findAllByVagas(nivel)
                .forEach(postBanda -> postBanda.getVagas().remove(nivel));
        nivelRepository.delete(nivel);
    }

    private NivelDTO mapToDTO(Nivel nivel, NivelDTO nivelDTO) {
        nivelDTO.setIdNivel(nivel.getIdNivel());
        nivelDTO.setExperiencia(nivel.getExperiencia());
        nivelDTO.setIdInstrumento(nivel.getIdInstrumento() == null ? null : nivel.getIdInstrumento().getIdInstrumento());
        nivelDTO.setIdUser(nivel.getUsuario() == null ? null : nivel.getUsuario().getIdUser());
        return nivelDTO;
    }

    private Nivel mapToEntity(NivelDTO nivelDTO, Nivel nivel) {
        nivel.setExperiencia(nivelDTO.getExperiencia());
        Instrumento idInstrumento = nivelDTO.getIdInstrumento() == null ? null : instrumentoRepository.findById(nivelDTO.getIdInstrumento())
                .orElseThrow(() -> new NotFoundException("Instrumento not found"));
        Usuario usuario = nivelDTO.getIdUser() == null ? null : usuarioRepository.findById(nivelDTO.getIdUser())
                .orElseThrow(() -> new NotFoundException("User not found"));
        nivel.setIdInstrumento(idInstrumento);
        nivel.setUsuario(usuario);
        return nivel;
    }


}

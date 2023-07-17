package br.com.pp.band_board.tipo_musical;

import br.com.pp.band_board.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TipoMusicalService {

    private final TipoMusicalRepository tipoMusicalRepository;

    public TipoMusicalService(final TipoMusicalRepository tipoMusicalRepository) {
        this.tipoMusicalRepository = tipoMusicalRepository;
    }

    public List<TipoMusicalDTO> findAll() {
        final List<TipoMusical> tipoMusicals = tipoMusicalRepository.findAll(Sort.by("id"));
        return tipoMusicals.stream()
                .map(tipoMusical -> mapToDTO(tipoMusical, new TipoMusicalDTO()))
                .toList();
    }

    public TipoMusicalDTO get(final Long id) {
        return tipoMusicalRepository.findById(id)
                .map(tipoMusical -> mapToDTO(tipoMusical, new TipoMusicalDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TipoMusicalDTO tipoMusicalDTO) {
        final TipoMusical tipoMusical = new TipoMusical();
        mapToEntity(tipoMusicalDTO, tipoMusical);
        return tipoMusicalRepository.save(tipoMusical).getId();
    }

    public void update(final Long id, final TipoMusicalDTO tipoMusicalDTO) {
        final TipoMusical tipoMusical = tipoMusicalRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tipoMusicalDTO, tipoMusical);
        tipoMusicalRepository.save(tipoMusical);
    }

    public void delete(final Long id) {
        tipoMusicalRepository.deleteById(id);
    }

    private TipoMusicalDTO mapToDTO(final TipoMusical tipoMusical,
            final TipoMusicalDTO tipoMusicalDTO) {
        tipoMusicalDTO.setId(tipoMusical.getId());
        tipoMusicalDTO.setNomeTipo(tipoMusical.getNomeTipo());
        return tipoMusicalDTO;
    }

    private TipoMusical mapToEntity(final TipoMusicalDTO tipoMusicalDTO,
            final TipoMusical tipoMusical) {
        tipoMusical.setNomeTipo(tipoMusicalDTO.getNomeTipo());
        return tipoMusical;
    }

}

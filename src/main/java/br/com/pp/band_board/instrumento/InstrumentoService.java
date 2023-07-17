package br.com.pp.band_board.instrumento;

import br.com.pp.band_board.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InstrumentoService {

    private final InstrumentoRepository instrumentoRepository;

    public InstrumentoService(final InstrumentoRepository instrumentoRepository) {
        this.instrumentoRepository = instrumentoRepository;
    }

    public List<InstrumentoDTO> findAll() {
        final List<Instrumento> instrumentos = instrumentoRepository.findAll(Sort.by("idInstrumento"));
        return instrumentos.stream()
                .map(instrumento -> mapToDTO(instrumento, new InstrumentoDTO()))
                .toList();
    }

    public InstrumentoDTO get(final Long idInstrumento) {
        return instrumentoRepository.findById(idInstrumento)
                .map(instrumento -> mapToDTO(instrumento, new InstrumentoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final InstrumentoDTO instrumentoDTO) {
        final Instrumento instrumento = new Instrumento();
        mapToEntity(instrumentoDTO, instrumento);
        return instrumentoRepository.save(instrumento).getIdInstrumento();
    }

    public void update(final Long idInstrumento, final InstrumentoDTO instrumentoDTO) {
        final Instrumento instrumento = instrumentoRepository.findById(idInstrumento)
                .orElseThrow(NotFoundException::new);
        mapToEntity(instrumentoDTO, instrumento);
        instrumentoRepository.save(instrumento);
    }

    public void delete(final Long idInstrumento) {
        instrumentoRepository.deleteById(idInstrumento);
    }

    private InstrumentoDTO mapToDTO(final Instrumento instrumento,
            final InstrumentoDTO instrumentoDTO) {
        instrumentoDTO.setIdInstrumento(instrumento.getIdInstrumento());
        instrumentoDTO.setNomeInstrumento(instrumento.getNomeInstrumento());
        return instrumentoDTO;
    }

    private Instrumento mapToEntity(final InstrumentoDTO instrumentoDTO,
            final Instrumento instrumento) {
        instrumento.setNomeInstrumento(instrumentoDTO.getNomeInstrumento());
        return instrumento;
    }

    public boolean nomeInstrumentoExists(final INSTRUMENTOS nomeInstrumento) {
        return instrumentoRepository.existsByNomeInstrumento(nomeInstrumento);
    }

}

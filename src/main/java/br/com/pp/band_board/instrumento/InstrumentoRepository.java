package br.com.pp.band_board.instrumento;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InstrumentoRepository extends JpaRepository<Instrumento, Long> {

    boolean existsByNomeInstrumento(INSTRUMENTOS nomeInstrumento);

}

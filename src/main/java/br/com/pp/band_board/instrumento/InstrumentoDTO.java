package br.com.pp.band_board.instrumento;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InstrumentoDTO {

    private Long idInstrumento;

    @NotNull
    private INSTRUMENTOS nomeInstrumento;

}

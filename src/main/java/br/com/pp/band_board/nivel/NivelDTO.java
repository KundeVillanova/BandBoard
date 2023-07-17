package br.com.pp.band_board.nivel;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NivelDTO {

    private Long idNivel;
    private EXPERIENCIA experiencia;
    private Long idInstrumento;
    private Long idUser;
}

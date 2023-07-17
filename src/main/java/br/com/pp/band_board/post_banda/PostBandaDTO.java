package br.com.pp.band_board.post_banda;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostBandaDTO {

    private Long idBanda;

    @NotNull
    @Size(max = 255)
    private String tituloBanda;

    @NotNull
    @Size(max = 510)
    private String descricao;

    private List<Long> vagas;

    private Long idTipoMusical;

    private Long idUser;
}

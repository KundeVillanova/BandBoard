package br.com.pp.band_board.post_show;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostShowDTO {

    private Long idShow;

    @NotNull
    @Size(max = 255)
    private String tituloShow;

    @Size(max = 300)
    private String descricao;

    private LocalDate dia;

    @Size(max = 255)
    private String hora;

    @Size(max = 255)
    private String lugar;

    private Integer ingressos;

    private Long idTipoShow;

    private Long idUser;

}

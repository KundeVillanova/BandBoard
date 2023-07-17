package br.com.pp.band_board.usuario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private Long idUser;

    @NotNull
    @Size(max = 255)
    private String nome;

    @NotNull
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String dataNascimento;

    @Size(max = 255)
    private String celular;

    @NotNull
    @Size(max = 255)
    private String senha;

    private List<Long> tiposMusicais;

    private List<Long> bandas;

    private List<Long> shows;

    private List<Long> experiencias;

}

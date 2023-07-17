package br.com.pp.band_board.nivel;

import br.com.pp.band_board.instrumento.Instrumento;
import br.com.pp.band_board.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Nivel {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long idNivel;

    @Column
    @Enumerated(EnumType.STRING)
    private EXPERIENCIA experiencia;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instrumento_id", unique = true)
    private Instrumento idInstrumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private Usuario usuario;

}

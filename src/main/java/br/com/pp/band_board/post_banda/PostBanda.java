package br.com.pp.band_board.post_banda;

import br.com.pp.band_board.nivel.Nivel;
import br.com.pp.band_board.tipo_musical.TipoMusical;
import br.com.pp.band_board.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PostBanda {

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
    private Long idBanda;

    @Column(nullable = false)
    private String tituloBanda;

    @Column(nullable = false, length = 510)
    private String descricao;

    @ManyToMany
    @JoinTable(
            name = "vagas",
            joinColumns = @JoinColumn(name = "id_banda"),
            inverseJoinColumns = @JoinColumn(name = "id_nivel")
    )
    private Set<Nivel> vagas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_musical_id")
    private TipoMusical idTipoMusical;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private Usuario usuario;

}

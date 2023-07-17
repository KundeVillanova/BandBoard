package br.com.pp.band_board.usuario;

import br.com.pp.band_board.nivel.Nivel;
import br.com.pp.band_board.post_banda.PostBanda;
import br.com.pp.band_board.post_show.PostShow;
import br.com.pp.band_board.tipo_musical.TipoMusical;
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
public class Usuario {

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
    private Long idUser;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column
    private String dataNascimento;

    @Column
    private String celular;

    @Column(nullable = false)
    private String senha;

    @ManyToMany
    @JoinTable(
            name = "usuario_tipos_musicais",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo_musical")
    )
    private Set<TipoMusical> tiposMusicais;


    @ManyToMany
    @JoinTable(
            name = "usuarios_post_banda",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_banda")
    )
    private Set<PostBanda> bandas;

    @ManyToMany
    @JoinTable(
            name = "usuario_shows",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_show")
    )
    private Set<PostShow> shows;

    @ManyToMany
    @JoinTable(
            name = "usuario_experiencia",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_nivel")
    )
    private Set<Nivel> experiencias;

}

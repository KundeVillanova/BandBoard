package br.com.pp.band_board.usuario;

import br.com.pp.band_board.nivel.Nivel;
import br.com.pp.band_board.post_banda.PostBanda;
import br.com.pp.band_board.post_show.PostShow;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    List<Usuario> findAllByExperiencias(Nivel nivel);

    List<Usuario> findAllByBandas(PostBanda postBanda);

    List<Usuario> findAllByShows(PostShow postShow);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO usuario_tipos_musicais (id_user, id_tipo_musical) VALUES (:idUser, :idTipoMusical)")
    void adicionarTipoMusical(@Param("idUser") Long idUser, @Param("idTipoMusical") Long idTipoMusical);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO usuario_experiencia (id_user, id_nivel) VALUES (:idUser, :idNivel)")
    void adicionarNivelInstrumento(@Param("idUser") Long idUser, @Param("idNivel") Long idNivel);

    @Transactional
    default void adicionarNiveisInstrumentos(Long idUser, List<Long> niveisIds) {
        for (Long idNivel : niveisIds) {
            adicionarNivelInstrumento(idUser, idNivel);
        }
    }

}

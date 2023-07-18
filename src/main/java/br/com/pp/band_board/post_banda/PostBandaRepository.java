package br.com.pp.band_board.post_banda;

import br.com.pp.band_board.nivel.Nivel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostBandaRepository extends JpaRepository<PostBanda, Long> {

    List<PostBanda> findAllByVagas(Nivel nivel);


    List<PostBanda> findByUsuarioIdUser(Long idUser);
}

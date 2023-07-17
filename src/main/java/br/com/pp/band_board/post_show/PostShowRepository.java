package br.com.pp.band_board.post_show;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostShowRepository extends JpaRepository<PostShow, Long> {
    List<PostShow> findByUsuarioIdUser(Long idUser);
}

package br.com.pp.band_board.post_show;

import br.com.pp.band_board.tipo_musical.TipoMusical;
import br.com.pp.band_board.tipo_musical.TipoMusicalRepository;
import br.com.pp.band_board.usuario.Usuario;
import br.com.pp.band_board.usuario.UsuarioRepository;
import br.com.pp.band_board.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class PostShowService {

    private final PostShowRepository postShowRepository;
    private final TipoMusicalRepository tipoMusicalRepository;
    private final UsuarioRepository usuarioRepository;

    public PostShowService(final PostShowRepository postShowRepository,
            final TipoMusicalRepository tipoMusicalRepository,
            final UsuarioRepository usuarioRepository) {
        this.postShowRepository = postShowRepository;
        this.tipoMusicalRepository = tipoMusicalRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<PostShowDTO> findAll() {
        final List<PostShow> postShows = postShowRepository.findAll(Sort.by("idShow"));
        return postShows.stream()
                .map(postShow -> mapToDTO(postShow, new PostShowDTO()))
                .toList();
    }

    public PostShowDTO get(final Long idShow) {
        return postShowRepository.findById(idShow)
                .map(postShow -> mapToDTO(postShow, new PostShowDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PostShowDTO postShowDTO) {
        final PostShow postShow = new PostShow();
        mapToEntity(postShowDTO, postShow);
        return postShowRepository.save(postShow).getIdShow();
    }

    public void update(final Long idShow, final PostShowDTO postShowDTO) {
        final PostShow postShow = postShowRepository.findById(idShow)
                .orElseThrow(NotFoundException::new);
        mapToEntity(postShowDTO, postShow);
        postShowRepository.save(postShow);
    }

    public void delete(final Long idShow) {
        final PostShow postShow = postShowRepository.findById(idShow)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        usuarioRepository.findAllByShows(postShow)
                .forEach(usuario -> usuario.getShows().remove(postShow));
        postShowRepository.delete(postShow);
    }

    private PostShowDTO mapToDTO(final PostShow postShow, final PostShowDTO postShowDTO) {
        postShowDTO.setIdShow(postShow.getIdShow());
        postShowDTO.setTituloShow(postShow.getTituloShow());
        postShowDTO.setDescricao(postShow.getDescricao());
        postShowDTO.setDia(postShow.getDia());
        postShowDTO.setHora(postShow.getHora());
        postShowDTO.setLugar(postShow.getLugar());
        postShowDTO.setIngressos(postShow.getIngressos());
        postShowDTO.setIdTipoShow(postShow.getIdTipoShow() == null ? null : postShow.getIdTipoShow().getId());
        postShowDTO.setIdUser(postShow.getUsuario().getIdUser());
        return postShowDTO;
    }

    private PostShow mapToEntity(final PostShowDTO postShowDTO, final PostShow postShow) {
        postShow.setTituloShow(postShowDTO.getTituloShow());
        postShow.setDescricao(postShowDTO.getDescricao());
        postShow.setDia(postShowDTO.getDia());
        postShow.setHora(postShowDTO.getHora());
        postShow.setLugar(postShowDTO.getLugar());
        postShow.setIngressos(postShowDTO.getIngressos());
        final TipoMusical idTipoShow = postShowDTO.getIdTipoShow() == null ? null : tipoMusicalRepository.findById(postShowDTO.getIdTipoShow())
                .orElseThrow(() -> new NotFoundException("idTipoShow not found"));
        postShow.setIdTipoShow(idTipoShow);
        final Usuario usuario = usuarioRepository.findById(postShowDTO.getIdUser())
                .orElseThrow(NotFoundException::new);
        postShow.setUsuario(usuario);
        return postShow;
    }

    public List<PostShowDTO> getPostShowsByUserId(final Long idUser) {
        final List<PostShow> postShows = postShowRepository.findByUsuarioIdUser(idUser);
        return postShows.stream()
                .map(postShow -> mapToDTO(postShow, new PostShowDTO()))
                .collect(Collectors.toList());
    }

}

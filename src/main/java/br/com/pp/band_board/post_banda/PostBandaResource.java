package br.com.pp.band_board.post_banda;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/postBandas", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class PostBandaResource {

    private final PostBandaService postBandaService;

    public PostBandaResource(final PostBandaService postBandaService) {
        this.postBandaService = postBandaService;
    }

    @GetMapping
    public ResponseEntity<List<PostBandaDTO>> getAllPostBandas() {
        return ResponseEntity.ok(postBandaService.findAll());
    }

    @GetMapping("/{idBanda}")
    public ResponseEntity<PostBandaDTO> getPostBanda(
            @PathVariable(name = "idBanda") final Long idBanda) {
        return ResponseEntity.ok(postBandaService.get(idBanda));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPostBanda(
            @RequestBody @Valid final PostBandaDTO postBandaDTO) {
        final Long createdIdBanda = postBandaService.create(postBandaDTO);
        return new ResponseEntity<>(createdIdBanda, HttpStatus.CREATED);
    }

    @PutMapping("/{idBanda}")
    public ResponseEntity<Long> updatePostBanda(@PathVariable(name = "idBanda") final Long idBanda,
            @RequestBody @Valid final PostBandaDTO postBandaDTO) {
        postBandaService.update(idBanda, postBandaDTO);
        return ResponseEntity.ok(idBanda);
    }

    @DeleteMapping("/{idBanda}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePostBanda(
            @PathVariable(name = "idBanda") final Long idBanda) {
        postBandaService.delete(idBanda);
        return ResponseEntity.noContent().build();
    }

}

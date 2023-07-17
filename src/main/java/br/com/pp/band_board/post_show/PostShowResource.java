package br.com.pp.band_board.post_show;

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
@RequestMapping(value = "/api/postShows", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class PostShowResource {

    private final PostShowService postShowService;

    public PostShowResource(final PostShowService postShowService) {
        this.postShowService = postShowService;
    }

    @GetMapping
    public ResponseEntity<List<PostShowDTO>> getAllPostShows() {
        return ResponseEntity.ok(postShowService.findAll());
    }

    @GetMapping("/{idShow}")
    public ResponseEntity<PostShowDTO> getPostShow(
            @PathVariable(name = "idShow") final Long idShow) {
        return ResponseEntity.ok(postShowService.get(idShow));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPostShow(@RequestBody @Valid final PostShowDTO postShowDTO) {
        final Long createdIdShow = postShowService.create(postShowDTO);
        return new ResponseEntity<>(createdIdShow, HttpStatus.CREATED);
    }

    @PutMapping("/{idShow}")
    public ResponseEntity<Long> updatePostShow(@PathVariable(name = "idShow") final Long idShow,
            @RequestBody @Valid final PostShowDTO postShowDTO) {
        postShowService.update(idShow, postShowDTO);
        return ResponseEntity.ok(idShow);
    }

    @DeleteMapping("/{idShow}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePostShow(@PathVariable(name = "idShow") final Long idShow) {
        postShowService.delete(idShow);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<List<PostShowDTO>> getPostShowsByUserId(
            @PathVariable(name = "idUser") final Long idUser) {
        return ResponseEntity.ok(postShowService.getPostShowsByUserId(idUser));
    }
}

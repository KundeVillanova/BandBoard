package br.com.pp.band_board.tipo_musical;

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
@RequestMapping(value = "/api/tipoMusicals", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoMusicalResource {

    private final TipoMusicalService tipoMusicalService;

    public TipoMusicalResource(final TipoMusicalService tipoMusicalService) {
        this.tipoMusicalService = tipoMusicalService;
    }

    @GetMapping
    public ResponseEntity<List<TipoMusicalDTO>> getAllTipoMusicals() {
        return ResponseEntity.ok(tipoMusicalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMusicalDTO> getTipoMusical(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(tipoMusicalService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTipoMusical(
            @RequestBody @Valid final TipoMusicalDTO tipoMusicalDTO) {
        final Long createdId = tipoMusicalService.create(tipoMusicalDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTipoMusical(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TipoMusicalDTO tipoMusicalDTO) {
        tipoMusicalService.update(id, tipoMusicalDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTipoMusical(@PathVariable(name = "id") final Long id) {
        tipoMusicalService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

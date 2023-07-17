package br.com.pp.band_board.instrumento;

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
@RequestMapping(value = "/api/instrumentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class InstrumentoResource {

    private final InstrumentoService instrumentoService;

    public InstrumentoResource(final InstrumentoService instrumentoService) {
        this.instrumentoService = instrumentoService;
    }

    @GetMapping
    public ResponseEntity<List<InstrumentoDTO>> getAllInstrumentos() {
        return ResponseEntity.ok(instrumentoService.findAll());
    }

    @GetMapping("/{idInstrumento}")
    public ResponseEntity<InstrumentoDTO> getInstrumento(
            @PathVariable(name = "idInstrumento") final Long idInstrumento) {
        return ResponseEntity.ok(instrumentoService.get(idInstrumento));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createInstrumento(
            @RequestBody @Valid final InstrumentoDTO instrumentoDTO) {
        final Long createdIdInstrumento = instrumentoService.create(instrumentoDTO);
        return new ResponseEntity<>(createdIdInstrumento, HttpStatus.CREATED);
    }

    @PutMapping("/{idInstrumento}")
    public ResponseEntity<Long> updateInstrumento(
            @PathVariable(name = "idInstrumento") final Long idInstrumento,
            @RequestBody @Valid final InstrumentoDTO instrumentoDTO) {
        instrumentoService.update(idInstrumento, instrumentoDTO);
        return ResponseEntity.ok(idInstrumento);
    }

    @DeleteMapping("/{idInstrumento}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteInstrumento(
            @PathVariable(name = "idInstrumento") final Long idInstrumento) {
        instrumentoService.delete(idInstrumento);
        return ResponseEntity.noContent().build();
    }

}

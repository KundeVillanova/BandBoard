package br.com.pp.band_board.usuario;

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
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class UsuarioResource {
    private final UsuarioService usuarioService;
    public UsuarioResource(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable(name = "idUser") final Long idUser) {
        return ResponseEntity.ok(usuarioService.get(idUser));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Long createdIdUser = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdIdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<Long> updateUsuario(@PathVariable(name = "idUser") final Long idUser,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(idUser, usuarioDTO);
        return ResponseEntity.ok(idUser);
    }

    @DeleteMapping("/{idUser}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuario(@PathVariable(name = "idUser") final Long idUser) {
        usuarioService.delete(idUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nome/{nomeUsuario}")
    public ResponseEntity<UsuarioDTO> getUsuarioByNome(@PathVariable(name = "nomeUsuario") final String nomeUsuario) {
        return ResponseEntity.ok(usuarioService.getByNome(nomeUsuario));
    }

    @PostMapping("/{idUser}/tipos-musicais")
    public ResponseEntity<Void> adicionarTiposMusicais(
            @PathVariable(name = "idUser") final Long idUser,
            @RequestBody final List<Long> tiposMusicaisIds
    ) {
        usuarioService.adicionarTiposMusicais(idUser, tiposMusicaisIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idUser}/niveis-instrumentos")
    public ResponseEntity<Void> adicionarNiveisInstrumentos(
            @PathVariable(name = "idUser") final Long idUser,
            @RequestBody final List<Long> niveisIds
    ) {
        usuarioService.adicionarNiveisInstrumentos(idUser, niveisIds);
        return ResponseEntity.ok().build();
    }

}

package br.com.equipamentos.emprestimos.msusuario.controller;

import br.com.equipamentos.emprestimos.msusuario.dto.UsuarioRequestDTO;
import br.com.equipamentos.emprestimos.msusuario.dto.UsuarioResponseDTO;
import br.com.equipamentos.emprestimos.msusuario.service.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(
            @Valid @RequestBody UsuarioRequestDTO dto) {

        UsuarioResponseDTO usuario = service.cadastrar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.id())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @PathVariable UUID id) {

        UsuarioResponseDTO usuario = service.buscarPorId(id);

        return ResponseEntity.ok(usuario);
    }
}
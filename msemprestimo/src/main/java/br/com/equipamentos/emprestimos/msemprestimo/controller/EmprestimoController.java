package br.com.equipamentos.emprestimos.msemprestimo.controller;

import br.com.equipamentos.emprestimos.msemprestimo.dto.EmprestimoRequestDTO;
import br.com.equipamentos.emprestimos.msemprestimo.dto.EmprestimoResponseDTO;
import br.com.equipamentos.emprestimos.msemprestimo.service.EmprestimoService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/emprestimos")
public class EmprestimoController {

    private final EmprestimoService service;

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> solicitar(
            @Valid @RequestBody EmprestimoRequestDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.solicitar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDTO> buscarPorId(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarPorUsuario(
            @PathVariable UUID usuarioId
    ) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @PatchMapping("/{id}/devolucao")
    public ResponseEntity<EmprestimoResponseDTO> devolver(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(service.devolver(id));
    }
}

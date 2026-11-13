
package br.com.equipamentos.emprestimos.msequipamento.controller;

import br.com.equipamentos.emprestimos.msequipamento.dto.EquipamentoRequestDTO;
import br.com.equipamentos.emprestimos.msequipamento.dto.EquipamentoResponseDTO;
import br.com.equipamentos.emprestimos.msequipamento.service.EquipamentoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/equipamentos")
public class EquipamentoController {

    private final EquipamentoService service;

    public EquipamentoController(EquipamentoService service) {
        this.service = service;
    }

    // Cadastrar equipamento
    @PostMapping
    public ResponseEntity<EquipamentoResponseDTO> cadastrar(
            @Valid @RequestBody EquipamentoRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.cadastrar(dto));
    }

    // Consultar equipamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> buscarPorId(
            @PathVariable UUID id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Listar equipamentos disponíveis
    @GetMapping("/disponiveis")
    public ResponseEntity<List<EquipamentoResponseDTO>> listarDisponiveis() {

        return ResponseEntity.ok(service.listarDisponiveis());
    }

    // Reservar equipamento
    @PutMapping("/{id}/reservas/{emprestimoId}")
    public ResponseEntity<Void> reservar(
            @PathVariable UUID id,
            @PathVariable UUID emprestimoId) {

        service.reservar(id, emprestimoId);

        return ResponseEntity.noContent().build();
    }

    // Liberar equipamento
    @DeleteMapping("/{id}/reservas/{emprestimoId}")
    public ResponseEntity<Void> liberar(
            @PathVariable UUID id,
            @PathVariable UUID emprestimoId) {

        service.liberar(id, emprestimoId);

        return ResponseEntity.noContent().build();
    }
}

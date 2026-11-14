package br.com.equipamentos.emprestimos.msemprestimo.dto;

import br.com.equipamentos.emprestimos.msemprestimo.model.StatusEmprestimo;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmprestimoResponseDTO(

        UUID id,
        UUID usuarioId,
        UUID equipamentoId,
        LocalDateTime dataEmprestimo,
        LocalDateTime dataDevolucao,
        StatusEmprestimo status

) {
}
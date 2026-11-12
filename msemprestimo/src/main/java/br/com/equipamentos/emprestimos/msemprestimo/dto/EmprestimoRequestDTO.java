package br.com.equipamentos.emprestimos.msemprestimo.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EmprestimoRequestDTO(

        @NotNull(message = "O ID do usuário é obrigatório")
        UUID usuarioId,

        @NotNull(message = "O ID do equipamento é obrigatório")
        UUID equipamentoId

) {
}
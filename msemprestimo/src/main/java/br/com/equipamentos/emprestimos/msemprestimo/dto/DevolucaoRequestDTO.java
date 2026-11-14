package br.com.equipamentos.emprestimos.msemprestimo.dto;

import br.com.equipamentos.emprestimos.msemprestimo.model.CondicaoEquipamento;
import jakarta.validation.constraints.NotNull;

public record DevolucaoRequestDTO(
        @NotNull(message = "A condição é obrigatória")
        CondicaoEquipamento condicao
) {}

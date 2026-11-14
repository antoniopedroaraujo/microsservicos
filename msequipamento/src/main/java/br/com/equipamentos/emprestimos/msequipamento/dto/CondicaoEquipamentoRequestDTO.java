package br.com.equipamentos.emprestimos.msequipamento.dto;

import br.com.equipamentos.emprestimos.msequipamento.model.CondicaoEquipamento;
import jakarta.validation.constraints.NotNull;

public record CondicaoEquipamentoRequestDTO(
        @NotNull(message = "A condição é obrigatória")
        CondicaoEquipamento condicao
) {}

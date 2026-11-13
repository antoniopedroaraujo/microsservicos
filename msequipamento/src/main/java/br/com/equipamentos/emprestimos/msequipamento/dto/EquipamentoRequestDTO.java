
package br.com.equipamentos.emprestimos.msequipamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EquipamentoRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 150)
        String nome,

        @NotBlank(message = "O tipo é obrigatório")
        @Size(max = 50)
        String tipo,

        @NotBlank(message = "O patrimônio é obrigatório")
        @Size(max = 50)
        String patrimonio,

        @NotNull(message = "A disponibilidade é obrigatória")
        Boolean disponivel

) {}


package br.com.equipamentos.emprestimos.msequipamento.dto;

import java.util.UUID;

public record EquipamentoResponseDTO(

        UUID id,
        String nome,
        String tipo,
        String patrimonio,
        Boolean disponivel

) {}

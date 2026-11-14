package br.com.equipamentos.emprestimos.msequipamento.dto;

import br.com.equipamentos.emprestimos.msequipamento.model.CondicaoEquipamento;
import java.util.UUID;

public record EquipamentoResponseDTO(

        UUID id,
        String nome,
        String tipo,
        String patrimonio,
        Boolean disponivel,
        CondicaoEquipamento condicao

) {}

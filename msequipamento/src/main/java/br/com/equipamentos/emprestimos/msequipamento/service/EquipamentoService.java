
package br.com.equipamentos.emprestimos.msequipamento.service;

import br.com.equipamentos.emprestimos.msequipamento.dto.EquipamentoRequestDTO;
import br.com.equipamentos.emprestimos.msequipamento.dto.EquipamentoResponseDTO;
import br.com.equipamentos.emprestimos.msequipamento.dto.CondicaoEquipamentoRequestDTO;

import java.util.List;
import java.util.UUID;

public interface EquipamentoService {

    EquipamentoResponseDTO cadastrar(
            EquipamentoRequestDTO dto
    );

    EquipamentoResponseDTO buscarPorId(UUID id);

    EquipamentoResponseDTO alterarCondicao(UUID id, CondicaoEquipamentoRequestDTO dto);

    List<EquipamentoResponseDTO> listarDisponiveis();

    void reservar(UUID equipamentoId, UUID emprestimoId);

    void liberar(UUID equipamentoId, UUID emprestimoId);
}

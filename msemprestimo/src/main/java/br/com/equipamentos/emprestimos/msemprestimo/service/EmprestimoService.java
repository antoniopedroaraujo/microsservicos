package br.com.equipamentos.emprestimos.msemprestimo.service;

import br.com.equipamentos.emprestimos.msemprestimo.dto.EmprestimoRequestDTO;
import br.com.equipamentos.emprestimos.msemprestimo.dto.EmprestimoResponseDTO;

import java.util.List;
import java.util.UUID;

public interface EmprestimoService {

    EmprestimoResponseDTO solicitar(EmprestimoRequestDTO dto);

    EmprestimoResponseDTO buscarPorId(UUID id);

    List<EmprestimoResponseDTO> listarPorUsuario(UUID usuarioId);

    EmprestimoResponseDTO devolver(UUID id);
}

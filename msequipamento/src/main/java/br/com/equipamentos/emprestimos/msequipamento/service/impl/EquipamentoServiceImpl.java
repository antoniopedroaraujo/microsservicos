
package br.com.equipamentos.emprestimos.msequipamento.service.impl;

import br.com.equipamentos.emprestimos.msequipamento.dto.EquipamentoRequestDTO;
import br.com.equipamentos.emprestimos.msequipamento.dto.EquipamentoResponseDTO;
import br.com.equipamentos.emprestimos.msequipamento.dto.CondicaoEquipamentoRequestDTO;
import br.com.equipamentos.emprestimos.msequipamento.exception.EquipamentoNotFoundException;
import br.com.equipamentos.emprestimos.msequipamento.exception.EquipamentoConflictException;
import br.com.equipamentos.emprestimos.msequipamento.exception.EquipamentoIndisponivelException;
import br.com.equipamentos.emprestimos.msequipamento.model.Equipamento;
import br.com.equipamentos.emprestimos.msequipamento.model.CondicaoEquipamento;
import br.com.equipamentos.emprestimos.msequipamento.repository.EquipamentoRepository;
import br.com.equipamentos.emprestimos.msequipamento.service.EquipamentoService;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EquipamentoServiceImpl implements EquipamentoService {

    private final EquipamentoRepository repository;

    public EquipamentoServiceImpl(EquipamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public EquipamentoResponseDTO cadastrar(EquipamentoRequestDTO dto) {

        if (repository.existsByPatrimonio(dto.patrimonio())) {
            throw new EquipamentoConflictException(
                    "Já existe um equipamento com esse patrimônio"
            );
        }

        Equipamento equipamento = new Equipamento();

        equipamento.setNome(dto.nome());
        equipamento.setTipo(dto.tipo());
        equipamento.setPatrimonio(dto.patrimonio());
        equipamento.setDisponivel(dto.disponivel());
        equipamento.setCondicao(dto.condicao() != null ? dto.condicao() : CondicaoEquipamento.BOM);

        Equipamento salvo = repository.save(equipamento);

        return converterParaDTO(salvo);
    }

    @Override
    public EquipamentoResponseDTO buscarPorId(UUID id) {

        Equipamento equipamento = repository.findById(id)
                .orElseThrow(EquipamentoNotFoundException::new);

        return converterParaDTO(equipamento);
    }

    @Override
    @Transactional
    public EquipamentoResponseDTO alterarCondicao(UUID id, CondicaoEquipamentoRequestDTO dto) {
        Equipamento equipamento = repository.findById(id)
                .orElseThrow(EquipamentoNotFoundException::new);

        equipamento.setCondicao(dto.condicao());
        return converterParaDTO(repository.save(equipamento));
    }

    @Override
    public List<EquipamentoResponseDTO> listarDisponiveis() {

        return repository.findByDisponivelTrueOrderByNomeAsc()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Override
    @Transactional
    public void reservar(UUID equipamentoId, UUID emprestimoId) {

        Equipamento equipamento = repository.findById(equipamentoId)
                .orElseThrow(EquipamentoNotFoundException::new);

        if (emprestimoId.equals(equipamento.getReservaEmprestimoId())) {
            return;
        }

        if (!Boolean.TRUE.equals(equipamento.getDisponivel())) {
            throw new EquipamentoIndisponivelException();
        }

        int atualizados = repository.reservar(equipamentoId, emprestimoId);

        if (atualizados == 0) {

            Equipamento atualizado = repository.findById(equipamentoId)
                    .orElseThrow(EquipamentoNotFoundException::new);

            if (emprestimoId.equals(atualizado.getReservaEmprestimoId())) {
                return;
            }

            throw new EquipamentoConflictException(
                    "Equipamento já foi reservado por outra solicitação"
            );
        }
    }

    @Override
    @Transactional
    public void liberar(UUID equipamentoId, UUID emprestimoId) {

        Equipamento equipamento = repository.findById(equipamentoId)
                .orElseThrow(EquipamentoNotFoundException::new);

        if (!emprestimoId.equals(equipamento.getReservaEmprestimoId())) {
            throw new EquipamentoConflictException(
                    "A reserva não pertence a esse empréstimo"
            );
        }

        if (Boolean.TRUE.equals(equipamento.getDisponivel())) {
            return;
        }

        int atualizados = repository.liberar(equipamentoId, emprestimoId);

        if (atualizados == 0) {
            throw new EquipamentoConflictException(
                    "Não foi possível liberar a reserva"
            );
        }
    }

    private EquipamentoResponseDTO converterParaDTO(
            Equipamento equipamento) {

        return new EquipamentoResponseDTO(
                equipamento.getId(),
                equipamento.getNome(),
                equipamento.getTipo(),
                equipamento.getPatrimonio(),
                equipamento.getDisponivel(),
                equipamento.getCondicao()
        );
    }
}

package br.com.equipamentos.emprestimos.msemprestimo.service.impl;

import br.com.equipamentos.emprestimos.msemprestimo.client.EquipamentoClient;
import br.com.equipamentos.emprestimos.msemprestimo.client.UsuarioClient;
import br.com.equipamentos.emprestimos.msemprestimo.dto.EmprestimoRequestDTO;
import br.com.equipamentos.emprestimos.msemprestimo.dto.EmprestimoResponseDTO;
import br.com.equipamentos.emprestimos.msemprestimo.exception.EmprestimoConflictException;
import br.com.equipamentos.emprestimos.msemprestimo.exception.EmprestimoNotFoundException;
import br.com.equipamentos.emprestimos.msemprestimo.exception.RecursoNaoEncontradoException;
import br.com.equipamentos.emprestimos.msemprestimo.exception.ServicoIndisponivelException;
import br.com.equipamentos.emprestimos.msemprestimo.model.Emprestimo;
import br.com.equipamentos.emprestimos.msemprestimo.model.StatusEmprestimo;
import br.com.equipamentos.emprestimos.msemprestimo.repository.EmprestimoRepository;
import br.com.equipamentos.emprestimos.msemprestimo.service.EmprestimoService;

import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

    private final EmprestimoRepository repository;
    private final UsuarioClient usuarioClient;
    private final EquipamentoClient equipamentoClient;

    public EmprestimoServiceImpl(
            EmprestimoRepository repository,
            UsuarioClient usuarioClient,
            EquipamentoClient equipamentoClient
    ) {
        this.repository = repository;
        this.usuarioClient = usuarioClient;
        this.equipamentoClient = equipamentoClient;
    }

    @Override
    @Transactional
    public EmprestimoResponseDTO solicitar(EmprestimoRequestDTO dto) {

        // 1. Validar se o usuário existe
        try {
            usuarioClient.buscarPorId(dto.usuarioId());
        } catch (FeignException.NotFound e) {
            throw new RecursoNaoEncontradoException(
                    "Usuário não encontrado"
            );
        } catch (FeignException e) {
            throw new ServicoIndisponivelException(
                    "Não foi possível consultar o serviço de usuários"
            );
        }

        // 2. Gerar o ID antes de reservar o equipamento
        UUID emprestimoId = UUID.randomUUID();

        // 3. Reservar o equipamento
        try {
            equipamentoClient.reservar(
                    dto.equipamentoId(),
                    emprestimoId
            );
        } catch (FeignException.NotFound e) {
            throw new RecursoNaoEncontradoException(
                    "Equipamento não encontrado"
            );
        } catch (FeignException.Conflict e) {
            throw new EmprestimoConflictException(
                    "Equipamento indisponível para empréstimo"
            );
        } catch (FeignException e) {
            throw new ServicoIndisponivelException(
                    "Não foi possível reservar o equipamento"
            );
        }

        // 4. Criar o empréstimo somente após a reserva
        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setId(emprestimoId);
        emprestimo.setUsuarioId(dto.usuarioId());
        emprestimo.setEquipamentoId(dto.equipamentoId());
        emprestimo.setDataEmprestimo(LocalDateTime.now());
        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        // 5. Persistir o empréstimo
        try {
            return converterParaDTO(repository.save(emprestimo));
        } catch (RuntimeException e) {

            // Compensar a reserva caso a gravação falhe
            try {
                equipamentoClient.liberar(
                        dto.equipamentoId(),
                        emprestimoId
                );
            } catch (RuntimeException erroLiberacao) {
                e.addSuppressed(erroLiberacao);
            }

            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EmprestimoResponseDTO buscarPorId(UUID id) {

        Emprestimo emprestimo = repository.findById(id)
                .orElseThrow(EmprestimoNotFoundException::new);

        return converterParaDTO(emprestimo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmprestimoResponseDTO> listarPorUsuario(UUID usuarioId) {

        return repository
                .findByUsuarioIdOrderByDataEmprestimoDesc(usuarioId)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Override
    @Transactional
    public EmprestimoResponseDTO devolver(UUID id) {

        Emprestimo emprestimo = repository.findById(id)
                .orElseThrow(EmprestimoNotFoundException::new);

        if (emprestimo.getStatus() != StatusEmprestimo.ATIVO) {
            throw new EmprestimoConflictException(
                    "Somente empréstimos ativos podem ser devolvidos"
            );
        }

        // Liberar o equipamento no microsserviço correspondente
        try {
            equipamentoClient.liberar(
                    emprestimo.getEquipamentoId(),
                    emprestimo.getId()
            );
        } catch (FeignException.NotFound e) {
            throw new RecursoNaoEncontradoException(
                    "Equipamento não encontrado"
            );
        } catch (FeignException.Conflict e) {
            throw new EmprestimoConflictException(
                    "Não foi possível liberar o equipamento"
            );
        } catch (FeignException e) {
            throw new ServicoIndisponivelException(
                    "Não foi possível comunicar com o serviço de equipamentos"
            );
        }

        // Atualizar o empréstimo
        emprestimo.setDataDevolucao(LocalDateTime.now());
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);

        return converterParaDTO(repository.save(emprestimo));
    }

    private EmprestimoResponseDTO converterParaDTO(Emprestimo emprestimo) {

        return new EmprestimoResponseDTO(
                emprestimo.getId(),
                emprestimo.getUsuarioId(),
                emprestimo.getEquipamentoId(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao(),
                emprestimo.getStatus()
        );
    }
}

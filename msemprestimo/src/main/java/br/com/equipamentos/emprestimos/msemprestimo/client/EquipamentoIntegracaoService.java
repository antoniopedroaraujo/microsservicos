package br.com.equipamentos.emprestimos.msemprestimo.client;

import br.com.equipamentos.emprestimos.msemprestimo.dto.DevolucaoRequestDTO;
import br.com.equipamentos.emprestimos.msemprestimo.exception.RecursoNaoEncontradoException;
import br.com.equipamentos.emprestimos.msemprestimo.exception.ServicoIndisponivelException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EquipamentoIntegracaoService {

    private final EquipamentoClient equipamentoClient;

    public EquipamentoIntegracaoService(EquipamentoClient equipamentoClient) {
        this.equipamentoClient = equipamentoClient;
    }

    @CircuitBreaker(name = "equipamentoCondicao", fallbackMethod = "alterarCondicaoFallback")
    public void alterarCondicao(UUID equipamentoId, DevolucaoRequestDTO dto) {
        try {
            equipamentoClient.alterarCondicao(equipamentoId, dto);
        } catch (FeignException.NotFound e) {
            // Preserva o 404 e impede que erro de negócio seja contado como falha técnica.
            throw new RecursoNaoEncontradoException("Equipamento não encontrado");
        }
    }

    public void alterarCondicaoFallback(UUID equipamentoId, DevolucaoRequestDTO dto, Throwable erro) {
        if (erro instanceof RecursoNaoEncontradoException) {
            throw (RecursoNaoEncontradoException) erro;
        }
        throw new ServicoIndisponivelException(
                "Serviço de equipamentos indisponível para alterar a condição"
        );
    }
}

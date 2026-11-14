package br.com.equipamentos.emprestimos.msemprestimo.client;

import org.springframework.cloud.openfeign.FeignClient;
import br.com.equipamentos.emprestimos.msemprestimo.dto.DevolucaoRequestDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "msequipamento")
public interface EquipamentoClient {

    @PutMapping("/api/v1/equipamentos/{id}/reservas/{emprestimoId}")
    void reservar(
            @PathVariable("id") UUID equipamentoId,
            @PathVariable("emprestimoId") UUID emprestimoId
    );

    @DeleteMapping("/api/v1/equipamentos/{id}/reservas/{emprestimoId}")
    void liberar(
            @PathVariable("id") UUID equipamentoId,
            @PathVariable("emprestimoId") UUID emprestimoId
    );

    @PutMapping("/api/v1/equipamentos/{id}/condicao")
    void alterarCondicao(
            @PathVariable("id") UUID equipamentoId,
            @RequestBody DevolucaoRequestDTO dto
    );
}


package br.com.equipamentos.emprestimos.msequipamento.repository;

import br.com.equipamentos.emprestimos.msequipamento.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EquipamentoRepository
        extends JpaRepository<Equipamento, UUID> {

    // Verifica se já existe um equipamento com esse patrimônio
    boolean existsByPatrimonio(String patrimonio);

    // Busca equipamento pelo patrimônio
    Optional<Equipamento> findByPatrimonio(String patrimonio);

    // Lista somente equipamentos disponíveis
    List<Equipamento> findByDisponivelTrueOrderByNomeAsc();

    // Reserva atomicamente um equipamento disponível
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE Equipamento e
        SET e.disponivel = false,
            e.reservaEmprestimoId = :emprestimoId
        WHERE e.id = :equipamentoId
          AND e.disponivel = true
    """)
    int reservar(
            @Param("equipamentoId") UUID equipamentoId,
            @Param("emprestimoId") UUID emprestimoId
    );

    // Libera somente a reserva pertencente ao empréstimo informado
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE Equipamento e
        SET e.disponivel = true
        WHERE e.id = :equipamentoId
          AND e.reservaEmprestimoId = :emprestimoId
          AND e.disponivel = false
    """)
    int liberar(
            @Param("equipamentoId") UUID equipamentoId,
            @Param("emprestimoId") UUID emprestimoId
    );
}


package br.com.equipamentos.emprestimos.msequipamento.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(
        name = "tb_equipamentos",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_equipamentos_patrimonio",
                        columnNames = "patrimonio"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Equipamento {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false, length = 50)
    private String patrimonio;

    @Column(nullable = false)
    private Boolean disponivel;

    @Column(name = "reserva_emprestimo_id")
    private UUID reservaEmprestimoId;
}

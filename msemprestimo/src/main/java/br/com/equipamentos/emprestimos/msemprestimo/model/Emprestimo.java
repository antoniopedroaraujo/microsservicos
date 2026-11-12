package br.com.equipamentos.emprestimos.msemprestimo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_emprestimos")
@Getter
@Setter
@NoArgsConstructor
public class Emprestimo {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID usuarioId;

    @Column(nullable = false)
    private UUID equipamentoId;

    @Column(nullable = false)
    private LocalDateTime dataEmprestimo;

    private LocalDateTime dataDevolucao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEmprestimo status;
}
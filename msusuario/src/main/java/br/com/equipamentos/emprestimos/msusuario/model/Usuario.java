package br.com.equipamentos.emprestimos.msusuario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(
        name = "tb_usuarios",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_usuarios_email",
                        columnNames = "email"
                ),
                @UniqueConstraint(
                        name = "uk_usuarios_matricula",
                        columnNames = "matricula"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 180)
    private String email;

    @Column(nullable = false, length = 50)
    private String matricula;

}
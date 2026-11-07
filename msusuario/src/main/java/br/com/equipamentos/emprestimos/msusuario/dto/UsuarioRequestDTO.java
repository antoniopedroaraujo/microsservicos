package br.com.equipamentos.emprestimos.msusuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 150)
        String nome,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Informe um email válido")
        @Size(max = 180)
        String email,

        @NotBlank(message = "A matrícula é obrigatória")
        @Size(max = 50)
        String matricula

) {}

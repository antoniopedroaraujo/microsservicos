package br.com.equipamentos.emprestimos.msusuario.dto;

import java.util.UUID;

public record UsuarioResponseDTO(

        UUID id,
        String nome,
        String email,
        String matricula

) {}

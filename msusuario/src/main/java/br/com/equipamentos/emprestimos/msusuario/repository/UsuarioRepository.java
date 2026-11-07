package br.com.equipamentos.emprestimos.msusuario.repository;

import br.com.equipamentos.emprestimos.msusuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository
        extends JpaRepository<Usuario, UUID> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByMatricula(String matricula);

}

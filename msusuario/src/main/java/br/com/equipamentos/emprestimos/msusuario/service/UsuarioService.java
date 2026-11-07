package br.com.equipamentos.emprestimos.msusuario.service;

import br.com.equipamentos.emprestimos.msusuario.dto.UsuarioRequestDTO;
import br.com.equipamentos.emprestimos.msusuario.dto.UsuarioResponseDTO;
import br.com.equipamentos.emprestimos.msusuario.exception.UsuarioConflictException;
import br.com.equipamentos.emprestimos.msusuario.exception.UsuarioNotFoundException;
import br.com.equipamentos.emprestimos.msusuario.model.Usuario;
import br.com.equipamentos.emprestimos.msusuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(
            UsuarioRequestDTO dto) {

        String email = dto.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        String matricula = dto.matricula().trim();

        if (repository.existsByEmailIgnoreCase(email)) {
            throw new UsuarioConflictException(
                    "Este email já está cadastrado."
            );
        }

        if (repository.existsByMatricula(matricula)) {
            throw new UsuarioConflictException(
                    "Esta matrícula já está cadastrada."
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome().trim());
        usuario.setEmail(email);
        usuario.setMatricula(matricula);

        Usuario salvo = repository.save(usuario);

        return converterParaDTO(salvo);
    }

    public UsuarioResponseDTO buscarPorId(UUID id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNotFoundException(
                                "Usuário não encontrado: " + id
                        )
                );

        return converterParaDTO(usuario);
    }

    private UsuarioResponseDTO converterParaDTO(
            Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getMatricula()
        );
    }
}
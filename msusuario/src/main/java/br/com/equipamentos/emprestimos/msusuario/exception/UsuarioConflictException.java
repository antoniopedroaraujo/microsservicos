package br.com.equipamentos.emprestimos.msusuario.exception;

public class UsuarioConflictException
        extends RuntimeException {

    public UsuarioConflictException(String message) {
        super(message);
    }
}

package br.com.equipamentos.emprestimos.msusuario.exception;

public class UsuarioNotFoundException
        extends RuntimeException {

    public UsuarioNotFoundException(String message) {
        super(message);
    }
}

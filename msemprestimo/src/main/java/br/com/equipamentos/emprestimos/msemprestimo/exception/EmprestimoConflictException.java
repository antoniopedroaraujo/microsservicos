package br.com.equipamentos.emprestimos.msemprestimo.exception;

public class EmprestimoConflictException extends RuntimeException {

    public EmprestimoConflictException(String mensagem) {
        super(mensagem);
    }
}
package br.com.equipamentos.emprestimos.msemprestimo.exception;

public class EmprestimoNotFoundException extends RuntimeException {

    public EmprestimoNotFoundException() {
        super("Empréstimo não encontrado");
    }
}
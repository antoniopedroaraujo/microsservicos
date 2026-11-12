package br.com.equipamentos.emprestimos.msemprestimo.exception;

public class ServicoIndisponivelException extends RuntimeException {

    public ServicoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
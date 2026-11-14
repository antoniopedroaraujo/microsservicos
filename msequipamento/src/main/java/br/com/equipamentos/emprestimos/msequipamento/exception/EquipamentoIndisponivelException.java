
package br.com.equipamentos.emprestimos.msequipamento.exception;

public class EquipamentoIndisponivelException extends RuntimeException {

    public EquipamentoIndisponivelException() {
        super("Equipamento indisponível para empréstimo");
    }
}

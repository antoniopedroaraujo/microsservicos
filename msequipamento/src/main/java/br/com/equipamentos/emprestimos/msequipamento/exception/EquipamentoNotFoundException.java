
package br.com.equipamentos.emprestimos.msequipamento.exception;

public class EquipamentoNotFoundException extends RuntimeException {

    public EquipamentoNotFoundException() {
        super("Equipamento não encontrado");
    }
}

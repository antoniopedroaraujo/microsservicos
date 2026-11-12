package br.com.equipamentos.emprestimos.msequipamento.exception;

public class EquipamentoNotFoundException extends RuntimeException {
  public EquipamentoNotFoundException(String message) {
    super(message);
  }
}

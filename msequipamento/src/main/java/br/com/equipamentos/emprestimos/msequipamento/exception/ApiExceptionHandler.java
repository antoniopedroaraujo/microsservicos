package br.com.equipamentos.emprestimos.msequipamento.exception;

public class ApiExceptionHandler extends RuntimeException {
  public ApiExceptionHandler(String message) {
    super(message);
  }
}

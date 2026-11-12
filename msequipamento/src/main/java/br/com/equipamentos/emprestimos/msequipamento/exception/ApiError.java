package br.com.equipamentos.emprestimos.msequipamento.exception;

public class ApiError extends RuntimeException {
  public ApiError(String message) {
    super(message);
  }
}

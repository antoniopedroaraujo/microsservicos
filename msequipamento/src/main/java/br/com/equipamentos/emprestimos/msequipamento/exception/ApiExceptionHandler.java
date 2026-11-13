
package br.com.equipamentos.emprestimos.msequipamento.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EquipamentoNotFoundException.class)
    public ResponseEntity<ApiError> tratarNaoEncontrado(
            EquipamentoNotFoundException ex) {

        return gerarErro(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(EquipamentoIndisponivelException.class)
    public ResponseEntity<ApiError> tratarIndisponivel(
            EquipamentoIndisponivelException ex) {

        return gerarErro(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(EquipamentoConflictException.class)
    public ResponseEntity<ApiError> tratarConflito(
            EquipamentoConflictException ex) {

        return gerarErro(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> tratarIntegridade(
            DataIntegrityViolationException ex) {

        return gerarErro(
                HttpStatus.CONFLICT,
                "Já existe um equipamento com esse patrimônio"
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> tratarValidacao(
            MethodArgumentNotValidException ex) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(erro -> erro.getDefaultMessage())
                .orElse("Dados inválidos");

        return gerarErro(HttpStatus.BAD_REQUEST, mensagem);
    }

    private ResponseEntity<ApiError> gerarErro(
            HttpStatus status,
            String mensagem) {

        ApiError erro = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(erro);
    }
}

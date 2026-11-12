package br.com.equipamentos.emprestimos.msemprestimo.exception;

import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EmprestimoNotFoundException.class)
    public ResponseEntity<ApiError> tratarNaoEncontrado(
            EmprestimoNotFoundException e
    ) {
        return gerarErro(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiError> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException e
    ) {
        return gerarErro(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(EmprestimoConflictException.class)
    public ResponseEntity<ApiError> tratarConflito(
            EmprestimoConflictException e
    ) {
        return gerarErro(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(ServicoIndisponivelException.class)
    public ResponseEntity<ApiError> tratarServicoIndisponivel(
            ServicoIndisponivelException e
    ) {
        return gerarErro(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> tratarValidacao(
            MethodArgumentNotValidException e
    ) {
        String mensagem = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .findFirst()
                .orElse("Dados inválidos");

        return gerarErro(HttpStatus.BAD_REQUEST, mensagem);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ApiError> tratarRequisicaoInvalida(
            Exception e
    ) {
        return gerarErro(
                HttpStatus.BAD_REQUEST,
                "Requisição inválida"
        );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiError> tratarErroFeign(
            FeignException e
    ) {
        return gerarErro(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Falha na comunicação entre microsserviços"
        );
    }

    private ResponseEntity<ApiError> gerarErro(
            HttpStatus status,
            String mensagem
    ) {
        ApiError erro = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(erro);
    }
}


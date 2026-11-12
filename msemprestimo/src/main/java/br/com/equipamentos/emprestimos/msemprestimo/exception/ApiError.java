package br.com.equipamentos.emprestimos.msemprestimo.exception;

import java.time.LocalDateTime;

public record ApiError(
        int status,
        String erro,
        String mensagem,
        LocalDateTime dataHora
) {
}


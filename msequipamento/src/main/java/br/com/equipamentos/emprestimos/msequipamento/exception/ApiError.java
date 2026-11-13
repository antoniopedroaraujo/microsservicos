
package br.com.equipamentos.emprestimos.msequipamento.exception;

import java.time.LocalDateTime;

public record ApiError(
        int status,
        String erro,
        String mensagem,
        LocalDateTime dataHora
) {}

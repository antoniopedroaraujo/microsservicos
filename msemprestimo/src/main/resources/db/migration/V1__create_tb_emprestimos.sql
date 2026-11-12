CREATE TABLE tb_emprestimos (
                                id BINARY(16) NOT NULL,
                                usuario_id BINARY(16) NOT NULL,
                                equipamento_id BINARY(16) NOT NULL,
                                data_emprestimo DATETIME NOT NULL,
                                data_devolucao DATETIME NULL,
                                status VARCHAR(20) NOT NULL,

                                PRIMARY KEY (id),

                                INDEX idx_emprestimo_usuario (usuario_id),
                                INDEX idx_emprestimo_equipamento (equipamento_id),
                                INDEX idx_emprestimo_status (status)
);
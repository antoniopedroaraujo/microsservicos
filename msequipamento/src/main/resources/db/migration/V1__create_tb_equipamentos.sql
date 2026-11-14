
CREATE TABLE tb_equipamentos (

                                 id BINARY(16) NOT NULL,

                                 nome VARCHAR(150) NOT NULL,

                                 tipo VARCHAR(50) NOT NULL,

                                 patrimonio VARCHAR(50) NOT NULL,

                                 disponivel BOOLEAN NOT NULL,

                                 reserva_emprestimo_id BINARY(16) NULL,

                                 CONSTRAINT pk_equipamentos
                                     PRIMARY KEY (id),

                                 CONSTRAINT uk_equipamentos_patrimonio
                                     UNIQUE (patrimonio)

) ENGINE=InnoDB;

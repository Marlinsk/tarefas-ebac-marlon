CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    data_cadastro DATE NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_usuario_email ON usuario(email);

CREATE TABLE IF NOT EXISTS categoria_meme (
    id BIGINT AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    data_cadastro DATE NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT pk_categoria_meme PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_categoria_usuario ON categoria_meme(usuario_id);

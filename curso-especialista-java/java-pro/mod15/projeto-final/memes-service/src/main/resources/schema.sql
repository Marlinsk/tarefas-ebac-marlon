CREATE TABLE IF NOT EXISTS meme (
    id BIGINT AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    data_url CLOB NOT NULL,
    data_cadastro DATE NOT NULL,
    usuario_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    CONSTRAINT pk_meme PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_meme_usuario_id ON meme(usuario_id);
CREATE INDEX IF NOT EXISTS idx_meme_categoria_id ON meme(categoria_id);

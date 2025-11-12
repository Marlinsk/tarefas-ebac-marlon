-- Dados iniciais para testes do memes-service
-- Inserindo memes de exemplo para facilitar testes

INSERT INTO meme (nome, descricao, data_url, data_cadastro, usuario_id, categoria_id)
VALUES ('Gato Surpreso', 'Meme clássico do gato surpreso olhando', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==', CURRENT_DATE, 1, 1);

INSERT INTO meme (nome, descricao, data_url, data_cadastro, usuario_id, categoria_id)
VALUES ('Bug em Produção', 'Quando você encontra um bug crítico em produção às 18h da sexta-feira', 'http://example.com/bug-meme.png', CURRENT_DATE, 1, 2);

INSERT INTO meme (nome, descricao, data_url, data_cadastro, usuario_id, categoria_id)
VALUES ('Gol Impossível', 'Aquele gol que ninguém acreditou ser possível', 'http://example.com/gol-meme.png', CURRENT_DATE, 2, 3);

INSERT INTO meme (nome, descricao, data_url, data_cadastro, usuario_id, categoria_id)
VALUES ('Plot Twist Épico', 'Quando o filme surpreende com um final inesperado', 'http://example.com/plot-twist.png', CURRENT_DATE, 3, 4);

INSERT INTO meme (nome, descricao, data_url, data_cadastro, usuario_id, categoria_id)
VALUES ('Drop the Bass', 'Quando o DJ solta aquela batida que abala tudo', 'http://example.com/bass-drop.png', CURRENT_DATE, 2, 5);

package br.com.ebac.memelandia.memes.domain.exception;

public class CategoriaInvalidaException extends RuntimeException {
    public CategoriaInvalidaException(Long categoriaId) {
        super("Categoria com ID " + categoriaId + " não encontrada ou inválida");
    }

    public CategoriaInvalidaException(String message) {
        super(message);
    }
}

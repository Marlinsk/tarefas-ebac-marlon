package br.com.ebac.memelandia.categoria.domain.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(Long id) {
        super("Categoria não encontrada com id: " + id);
    }

    public CategoriaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}

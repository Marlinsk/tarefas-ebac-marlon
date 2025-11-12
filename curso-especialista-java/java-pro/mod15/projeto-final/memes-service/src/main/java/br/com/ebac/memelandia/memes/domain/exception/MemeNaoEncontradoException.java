package br.com.ebac.memelandia.memes.domain.exception;

public class MemeNaoEncontradoException extends RuntimeException {
    public MemeNaoEncontradoException(Long id) {
        super("Meme não encontrado com ID: " + id);
    }

    public MemeNaoEncontradoException(String message) {
        super(message);
    }
}

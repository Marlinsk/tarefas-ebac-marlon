package br.com.ebac.memelandia.memes.domain.exception;

public class UsuarioInvalidoException extends RuntimeException {
    public UsuarioInvalidoException(Long usuarioId) {
        super("Usuário com ID " + usuarioId + " não encontrado ou inválido");
    }

    public UsuarioInvalidoException(String message) {
        super(message);
    }
}

package br.com.ebac.memelandia.categoria.domain.exception;

public class UsuarioInvalidoException extends RuntimeException {
    public UsuarioInvalidoException(Long usuarioId) {
        super("Usuário com ID " + usuarioId + " não existe ou é inválido");
    }

    public UsuarioInvalidoException(String mensagem) {
        super(mensagem);
    }
}

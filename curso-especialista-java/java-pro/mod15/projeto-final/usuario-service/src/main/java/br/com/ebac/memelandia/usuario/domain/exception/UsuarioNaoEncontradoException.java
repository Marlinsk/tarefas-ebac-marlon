package br.com.ebac.memelandia.usuario.domain.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(Long id) {
        super("Usuário não encontrado com id: " + id);
    }

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

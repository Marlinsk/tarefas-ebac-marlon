package br.com.ebac.memelandia.usuario.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Usuario Domain Entity Tests")
public class UsuarioTest {

    @Test
    @DisplayName("Deve criar usuário válido usando factory method")
    void deveCriarUsuarioValidoUsandoFactoryMethod() {
        Usuario usuario = Usuario.criar("João Silva", "joao@example.com");

        assertNotNull(usuario);
        assertEquals("João Silva", usuario.getNome());
        assertEquals("joao@example.com", usuario.getEmail());
        assertEquals(LocalDate.now(), usuario.getDataCadastro());
        assertNull(usuario.getId());
    }

    @Test
    @DisplayName("Deve criar usuário com construtor completo")
    void deveCriarUsuarioComConstrutorCompleto() {
        LocalDate data = LocalDate.of(2024, 1, 15);

        Usuario usuario = new Usuario(1L, "Maria Santos", "maria@example.com", data);

        assertEquals(1L, usuario.getId());
        assertEquals("Maria Santos", usuario.getNome());
        assertEquals("maria@example.com", usuario.getEmail());
        assertEquals(data, usuario.getDataCadastro());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com nome vazio")
    void deveLancarExcecaoAoCriarUsuarioComNomeVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Usuario.criar("", "joao@example.com"));
        assertEquals("Nome não pode ser vazio", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com nome nulo")
    void deveLancarExcecaoAoCriarUsuarioComNomeNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Usuario.criar(null, "joao@example.com"));
        assertEquals("Nome não pode ser vazio", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com nome muito curto")
    void deveLancarExcecaoAoCriarUsuarioComNomeMuitoCurto() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Usuario.criar("Jo", "joao@example.com"));
        assertEquals("Nome deve ter pelo menos 3 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com nome muito longo")
    void deveLancarExcecaoAoCriarUsuarioComNomeMuitoLongo() {
        String nomeLongo = "a".repeat(101);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Usuario.criar(nomeLongo, "joao@example.com"));
        assertEquals("Nome deve ter no máximo 100 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar nome com tamanho mínimo válido (3 caracteres)")
    void deveAceitarNomeComTamanhoMinimo() {
        Usuario usuario = Usuario.criar("Ana", "ana@example.com");
        assertEquals("Ana", usuario.getNome());
    }

    @Test
    @DisplayName("Deve aceitar nome com tamanho máximo válido (100 caracteres)")
    void deveAceitarNomeComTamanhoMaximo() {
        String nomeMaximo = "a".repeat(100);
        Usuario usuario = Usuario.criar(nomeMaximo, "teste@example.com");
        assertEquals(nomeMaximo, usuario.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email vazio")
    void deveLancarExcecaoAoCriarUsuarioComEmailVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Usuario.criar("João Silva", ""));
        assertEquals("Email não pode ser vazio", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email nulo")
    void deveLancarExcecaoAoCriarUsuarioComEmailNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Usuario.criar("João Silva", null));
        assertEquals("Email não pode ser vazio", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email inválido")
    void deveLancarExcecaoAoCriarUsuarioComEmailInvalido() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Usuario.criar("João Silva", "email-invalido"));
        assertEquals("Email inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para email sem domínio")
    void deveLancarExcecaoParaEmailSemDominio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Usuario.criar("João Silva", "email@"));
        assertEquals("Email inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar email válido")
    void deveAceitarEmailValido() {
        Usuario usuario = Usuario.criar("João Silva", "joao.silva@example.com");
        assertEquals("joao.silva@example.com", usuario.getEmail());
    }

    @Test
    @DisplayName("Deve validar nome ao usar setter")
    void deveValidarNomeAoUsarSetter() {
        Usuario usuario = Usuario.criar("João Silva", "joao@example.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuario.setNome("Jo"));
        assertEquals("Nome deve ter pelo menos 3 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar email ao usar setter")
    void deveValidarEmailAoUsarSetter() {
        Usuario usuario = Usuario.criar("João Silva", "joao@example.com");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuario.setEmail("email-invalido"));
        assertEquals("Email inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve permitir atualizar nome válido")
    void devePermitirAtualizarNomeValido() {
        Usuario usuario = Usuario.criar("João Silva", "joao@example.com");
        usuario.setNome("João Pedro Silva");
        assertEquals("João Pedro Silva", usuario.getNome());
    }

    @Test
    @DisplayName("Deve permitir atualizar email válido")
    void devePermitirAtualizarEmailValido() {
        Usuario usuario = Usuario.criar("João Silva", "joao@example.com");
        usuario.setEmail("joao.novo@example.com");
        assertEquals("joao.novo@example.com", usuario.getEmail());
    }

    @Test
    @DisplayName("Deve comparar usuários por ID usando equals")
    void deveCompararUsuariosPorId() {
        Usuario usuario1 = new Usuario(1L, "João Silva", "joao@example.com", LocalDate.now());
        Usuario usuario2 = new Usuario(1L, "Maria Santos", "maria@example.com", LocalDate.now());
        Usuario usuario3 = new Usuario(2L, "João Silva", "joao@example.com", LocalDate.now());

        assertEquals(usuario1, usuario2);
        assertNotEquals(usuario1, usuario3);
        assertEquals(usuario1, usuario1);
    }

    @Test
    @DisplayName("Deve retornar false quando comparar com null")
    void deveRetornarFalseQuandoCompararComNull() {
        Usuario usuario = new Usuario(1L, "João Silva", "joao@example.com", LocalDate.now());
        assertNotEquals(null, usuario);
    }

    @Test
    @DisplayName("Deve retornar mesmo hashCode para usuários com mesmo ID")
    void deveRetornarMesmoHashCodeParaUsuariosComMesmoId() {
        Usuario usuario1 = new Usuario(1L, "João Silva", "joao@example.com", LocalDate.now());
        Usuario usuario2 = new Usuario(1L, "Maria Santos", "maria@example.com", LocalDate.now());
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }

    @Test
    @DisplayName("Deve gerar toString com informações do usuário")
    void deveGerarToStringComInformacoes() {
        LocalDate data = LocalDate.of(2024, 1, 15);
        Usuario usuario = new Usuario(1L, "João Silva", "joao@example.com", data);
        String resultado = usuario.toString();

        assertTrue(resultado.contains("id=1"));
        assertTrue(resultado.contains("nome='João Silva'"));
        assertTrue(resultado.contains("email='joao@example.com'"));
        assertTrue(resultado.contains("dataCadastro=2024-01-15"));
    }

    @Test
    @DisplayName("Deve permitir definir ID")
    void devePermitirDefinirId() {
        Usuario usuario = Usuario.criar("João Silva", "joao@example.com");
        usuario.setId(100L);
        assertEquals(100L, usuario.getId());
    }

}

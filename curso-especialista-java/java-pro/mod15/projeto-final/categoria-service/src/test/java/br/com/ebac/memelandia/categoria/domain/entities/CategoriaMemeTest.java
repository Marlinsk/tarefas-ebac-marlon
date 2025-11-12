package br.com.ebac.memelandia.categoria.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CategoriaMeme Domain Entity Tests")
public class CategoriaMemeTest {

    @Test
    @DisplayName("Deve criar categoria válida usando factory method")
    void deveCriarCategoriaValidaUsandoFactoryMethod() {
        CategoriaMeme categoria = CategoriaMeme.criar("Animais", "Memes de animais", 1L);

        assertNotNull(categoria);
        assertEquals("Animais", categoria.getNome());
        assertEquals("Memes de animais", categoria.getDescricao());
        assertEquals(LocalDate.now(), categoria.getDataCadastro());
        assertEquals(1L, categoria.getUsuarioId());
        assertNull(categoria.getId());
    }

    @Test
    @DisplayName("Deve criar categoria com construtor completo")
    void deveCriarCategoriaComConstrutorCompleto() {
        LocalDate data = LocalDate.of(2024, 1, 15);
        CategoriaMeme categoria = new CategoriaMeme(1L, "Filmes", "Memes de filmes", data, 1L);

        assertEquals(1L, categoria.getId());
        assertEquals("Filmes", categoria.getNome());
        assertEquals("Memes de filmes", categoria.getDescricao());
        assertEquals(data, categoria.getDataCadastro());
        assertEquals(1L, categoria.getUsuarioId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com nome vazio")
    void deveLancarExcecaoAoCriarCategoriaComNomeVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> CategoriaMeme.criar("", "Descrição válida", 1L));
        assertEquals("Nome não pode ser vazio", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com nome nulo")
    void deveLancarExcecaoAoCriarCategoriaComNomeNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> CategoriaMeme.criar(null, "Descrição válida", 1L));
        assertEquals("Nome não pode ser vazio", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com nome muito curto")
    void deveLancarExcecaoAoCriarCategoriaComNomeMuitoCurto() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> CategoriaMeme.criar("Ab", "Descrição válida", 1L));
        assertEquals("Nome deve ter pelo menos 3 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com nome muito longo")
    void deveLancarExcecaoAoCriarCategoriaComNomeMuitoLongo() {
        String nomeLongo = "a".repeat(101);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> CategoriaMeme.criar(nomeLongo, "Descrição válida", 1L));
        assertEquals("Nome não pode ter mais de 100 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar nome com tamanho mínimo válido")
    void deveAceitarNomeComTamanhoMinimo() {
        CategoriaMeme categoria = CategoriaMeme.criar("ABC", "Descrição válida", 1L);
        assertEquals("ABC", categoria.getNome());
    }

    @Test
    @DisplayName("Deve aceitar nome com tamanho máximo válido")
    void deveAceitarNomeComTamanhoMaximo() {
        String nomeMaximo = "a".repeat(100);
        CategoriaMeme categoria = CategoriaMeme.criar(nomeMaximo, "Descrição válida", 1L);
        assertEquals(nomeMaximo, categoria.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com descrição vazia")
    void deveLancarExcecaoAoCriarCategoriaComDescricaoVazia() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> CategoriaMeme.criar("Nome", "", 1L));
        assertEquals("Descrição não pode ser vazia", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com descrição nula")
    void deveLancarExcecaoAoCriarCategoriaComDescricaoNula() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> CategoriaMeme.criar("Nome", null, 1L));
        assertEquals("Descrição não pode ser vazia", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com descrição muito curta")
    void deveLancarExcecaoAoCriarCategoriaComDescricaoMuitoCurta() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> CategoriaMeme.criar("Nome", "Desc", 1L));
        assertEquals("Descrição deve ter pelo menos 5 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar descrição com tamanho mínimo válido")
    void deveAceitarDescricaoComTamanhoMinimo() {
        CategoriaMeme categoria = CategoriaMeme.criar("Nome", "12345", 1L);
        assertEquals("12345", categoria.getDescricao());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com usuarioId nulo")
    void deveLancarExcecaoAoCriarCategoriaComUsuarioIdNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> CategoriaMeme.criar("Nome", "Descrição", null));
        assertEquals("ID do usuário é obrigatório e deve ser maior que 0", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com usuarioId zero")
    void deveLancarExcecaoAoCriarCategoriaComUsuarioIdZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> CategoriaMeme.criar("Nome", "Descrição", 0L));
        assertEquals("ID do usuário é obrigatório e deve ser maior que 0", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar categoria com usuarioId negativo")
    void deveLancarExcecaoAoCriarCategoriaComUsuarioIdNegativo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> CategoriaMeme.criar("Nome", "Descrição", -1L));
        assertEquals("ID do usuário é obrigatório e deve ser maior que 0", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar nome ao usar setter")
    void deveValidarNomeAoUsarSetter() {
        CategoriaMeme categoria = CategoriaMeme.criar("Nome", "Descrição", 1L);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> categoria.setNome("Ab"));
        assertEquals("Nome deve ter pelo menos 3 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar descrição ao usar setter")
    void deveValidarDescricaoAoUsarSetter() {
        CategoriaMeme categoria = CategoriaMeme.criar("Nome", "Descrição", 1L);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> categoria.setDescricao("Desc"));
        assertEquals("Descrição deve ter pelo menos 5 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar usuarioId ao usar setter")
    void deveValidarUsuarioIdAoUsarSetter() {
        CategoriaMeme categoria = CategoriaMeme.criar("Nome", "Descrição", 1L);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> categoria.setUsuarioId(0L));
        assertEquals("ID do usuário é obrigatório e deve ser maior que 0", exception.getMessage());
    }

    @Test
    @DisplayName("Deve permitir atualizar nome válido")
    void devePermitirAtualizarNomeValido() {
        CategoriaMeme categoria = CategoriaMeme.criar("Nome", "Descrição", 1L);
        categoria.setNome("Novo Nome");
        assertEquals("Novo Nome", categoria.getNome());
    }

    @Test
    @DisplayName("Deve permitir atualizar descrição válida")
    void devePermitirAtualizarDescricaoValida() {
        CategoriaMeme categoria = CategoriaMeme.criar("Nome", "Descrição", 1L);
        categoria.setDescricao("Nova descrição válida");
        assertEquals("Nova descrição válida", categoria.getDescricao());
    }

    @Test
    @DisplayName("Deve permitir atualizar usuarioId válido")
    void devePermitirAtualizarUsuarioIdValido() {
        CategoriaMeme categoria = CategoriaMeme.criar("Nome", "Descrição", 1L);
        categoria.setUsuarioId(2L);
        assertEquals(2L, categoria.getUsuarioId());
    }

    @Test
    @DisplayName("Deve comparar categorias por ID usando equals")
    void deveCompararCategoriasPorId() {
        CategoriaMeme categoria1 = new CategoriaMeme(1L, "Nome1", "Desc1", LocalDate.now(), 1L);
        CategoriaMeme categoria2 = new CategoriaMeme(1L, "Nome2", "Desc2", LocalDate.now(), 2L);
        CategoriaMeme categoria3 = new CategoriaMeme(2L, "Nome1", "Desc1", LocalDate.now(), 1L);

        assertEquals(categoria1, categoria2);
        assertNotEquals(categoria1, categoria3);
        assertEquals(categoria1, categoria1);
    }

    @Test
    @DisplayName("Deve retornar false quando comparar com null")
    void deveRetornarFalseQuandoCompararComNull() {
        CategoriaMeme categoria = new CategoriaMeme(1L, "Nome", "Descrição", LocalDate.now(), 1L);
        assertNotEquals(null, categoria);
    }

    @Test
    @DisplayName("Deve retornar mesmo hashCode para categorias com mesmo ID")
    void deveRetornarMesmoHashCodeParaCategoriasComMesmoId() {
        CategoriaMeme categoria1 = new CategoriaMeme(1L, "Nome1", "Desc1", LocalDate.now(), 1L);
        CategoriaMeme categoria2 = new CategoriaMeme(1L, "Nome2", "Desc2", LocalDate.now(), 2L);
        assertEquals(categoria1.hashCode(), categoria2.hashCode());
    }

    @Test
    @DisplayName("Deve gerar toString com informações da categoria")
    void deveGerarToStringComInformacoes() {
        LocalDate data = LocalDate.of(2024, 1, 15);
        CategoriaMeme categoria = new CategoriaMeme(1L, "Animais", "Memes de animais", data, 1L);
        String resultado = categoria.toString();

        assertTrue(resultado.contains("id=1"));
        assertTrue(resultado.contains("nome='Animais'"));
        assertTrue(resultado.contains("descricao='Memes de animais'"));
        assertTrue(resultado.contains("dataCadastro=2024-01-15"));
        assertTrue(resultado.contains("usuarioId=1"));
    }

    @Test
    @DisplayName("Deve permitir definir ID")
    void devePermitirDefinirId() {
        CategoriaMeme categoria = CategoriaMeme.criar("Nome", "Descrição", 1L);
        categoria.setId(100L);
        assertEquals(100L, categoria.getId());
    }
}

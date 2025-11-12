package br.com.ebac.memelandia.usuario.application.usecase;

import br.com.ebac.memelandia.usuario.application.dto.UsuarioDTO;
import br.com.ebac.memelandia.usuario.domain.entity.Usuario;
import br.com.ebac.memelandia.usuario.domain.exception.EmailJaCadastradoException;
import br.com.ebac.memelandia.usuario.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CriarUsuarioUseCase Tests")
public class CriarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CriarUsuarioUseCase criarUsuarioUseCase;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João Silva");
        usuarioDTO.setEmail("joao@example.com");
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void deveCriarUsuarioComSucesso() {
        Usuario usuarioSalvo = new Usuario(1L, "João Silva", "joao@example.com", LocalDate.now());
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        UsuarioDTO resultado = criarUsuarioUseCase.executar(usuarioDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).existsByEmail("joao@example.com");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já existe")
    void deveLancarExcecaoQuandoEmailJaExiste() {
        when(usuarioRepository.existsByEmail(usuarioDTO.getEmail())).thenReturn(true);

        EmailJaCadastradoException exception = assertThrows(EmailJaCadastradoException.class, () -> criarUsuarioUseCase.executar(usuarioDTO));

        assertTrue(exception.getMessage().contains("joao@example.com"));
        verify(usuarioRepository, times(1)).existsByEmail(usuarioDTO.getEmail());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve propagar exceção do repositório")
    void devePropagarExcecaoDoRepositorio() {
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenThrow(new RuntimeException("Erro no banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> criarUsuarioUseCase.executar(usuarioDTO));

        assertEquals("Erro no banco de dados", exception.getMessage());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve salvar usuário com dados corretos")
    void deveSalvarUsuarioComDadosCorretos() {
        Usuario usuarioSalvo = new Usuario(2L, "Maria Santos", "maria@example.com", LocalDate.now());
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        UsuarioDTO novoUsuarioDTO = new UsuarioDTO();
        novoUsuarioDTO.setNome("Maria Santos");
        novoUsuarioDTO.setEmail("maria@example.com");

        UsuarioDTO resultado = criarUsuarioUseCase.executar(novoUsuarioDTO);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("Maria Santos", resultado.getNome());
        assertEquals("maria@example.com", resultado.getEmail());
    }

    @Test
    @DisplayName("Deve retornar DTO com ID gerado após salvar")
    void deveRetornarDTOComIdGerado() {
        Usuario usuarioComId = new Usuario(100L, "Pedro Costa", "pedro@example.com", LocalDate.now());
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioComId);

        UsuarioDTO resultado = criarUsuarioUseCase.executar(usuarioDTO);

        assertNotNull(resultado.getId());
        assertEquals(100L, resultado.getId());
    }

    @Test
    @DisplayName("Deve chamar repositório apenas uma vez ao criar usuário")
    void deveChamarRepositorioApenasUmaVez() {
        Usuario usuarioSalvo = new Usuario(1L, "Ana Lima", "ana@example.com", LocalDate.now());
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        criarUsuarioUseCase.executar(usuarioDTO);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve retornar data de cadastro no DTO")
    void deveRetornarDataCadastroNoDTO() {
        LocalDate dataEsperada = LocalDate.now();
        Usuario usuarioSalvo = new Usuario(1L, "João Silva", "joao@example.com", dataEsperada);
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        UsuarioDTO resultado = criarUsuarioUseCase.executar(usuarioDTO);

        assertNotNull(resultado.getDataCadastro());
        assertEquals(dataEsperada, resultado.getDataCadastro());
    }
}

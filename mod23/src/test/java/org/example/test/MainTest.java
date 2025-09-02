package org.example.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

import org.example.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setOut(originalOut);
        System.setIn(originalIn);
        outContent.close();
    }

    @Test
    void deveAgruparHomensEMulheres_ignoraSexoInvalido_eNormalizaMaiusculo() {
        String input = " Ana-F, João-M,  Pedro-m ,  maria-f , Alex-X ,  bea-F ";
        executarMainComEntrada(input);

        Map<String, List<String>> grupos = extrairGrupos(outContent.toString(StandardCharsets.UTF_8));

        assertEquals(Set.of("João", "Pedro"), new HashSet<>(grupos.getOrDefault("Homens", Collections.emptyList())), "Homens agrupados incorretamente");
        assertEquals(Set.of("Ana", "maria", "bea"), new HashSet<>(grupos.getOrDefault("Mulheres", Collections.emptyList())), "Mulheres agrupadas incorretamente");
    }

    @Test
    void deveTratarEspacosECaseDoSexo() {
        String input = "  Carla - f  ,  miguel - M  ";
        executarMainComEntrada(input);

        Map<String, List<String>> grupos = extrairGrupos(outContent.toString(StandardCharsets.UTF_8));

        assertEquals(Set.of("Carla"), new HashSet<>(grupos.getOrDefault("Mulheres", Collections.emptyList())));
        assertEquals(Set.of("miguel"), new HashSet<>(grupos.getOrDefault("Homens", Collections.emptyList())));
    }

    private void executarMainComEntrada(String entrada) {
        ByteArrayInputStream in = new ByteArrayInputStream((entrada + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        Main.main(new String[0]);
    }

    private Map<String, List<String>> extrairGrupos(String saidaCompleta) {
        Map<String, List<String>> grupos = new HashMap<>();
        Pattern p = Pattern.compile("^(Homens|Mulheres): \\[(.*?)]$", Pattern.MULTILINE);
        Matcher m = p.matcher(saidaCompleta);
        while (m.find()) {
            String grupo = m.group(1);
            String nomesStr = m.group(2).trim();
            List<String> nomes = nomesStr.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(nomesStr.split("\\s*,\\s*")));
            grupos.put(grupo, nomes);
        }

        return grupos;
    }
}

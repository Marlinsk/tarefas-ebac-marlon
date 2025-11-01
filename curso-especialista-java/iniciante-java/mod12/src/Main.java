import java.util.*;
import java.util.stream.Collectors;

// Example of use:
// Sophia Maria Cecília Maximo - Feminino, Martin Pedro Javier - Masculino, Eleanor Lamb - Feminino, Danilo Raul Heitor Mendes - Masculino, Mariane Marli Lindespoon - Feminino, Raul Rodrigo Caldeira - Masculino, Elaine Antonella Cláudia Araújo - Feminino, Maitê Florença Carvalho - Feminino

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, List<String>> genderGroup = new LinkedHashMap<>();
        genderGroup.put("Feminino", new ArrayList<>());
        genderGroup.put("Masculino", new ArrayList<>());

        System.out.println("Digite uma lista no formato 'Nome - Sexo(Feminino ou Masculino)' separados por vírgula :");
        String input2 = scanner.nextLine();

        Map<String, List<String>> list2 = Arrays.stream(input2.split("\\s*,\\s*")).map(p -> p.split("\\s*-\\s*")).filter(parts -> parts.length == 2).collect(Collectors.groupingBy(parts -> parts[1].trim(), () -> genderGroup, Collectors.mapping(parts -> parts[0].trim(), Collectors.toList())));
        list2.values().forEach(Collections::sort);

        // Masculino
        System.out.println("\nHomens:");
        list2.getOrDefault("Masculino", Collections.emptyList()).forEach(System.out::println);

        // Feminino
        System.out.println("\nMulheres:");
        list2.getOrDefault("Feminino", Collections.emptyList()).forEach(System.out::println);
    }


}
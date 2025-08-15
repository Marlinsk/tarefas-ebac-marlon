import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String input1 = "Sophia Maria Cecília Maximo, Martin Pedro Javier, Eleanor Lamb, Danilo Raul Heitor Mendes, Mariane Marli Lindespoon, Raul Rodrigo Caldeira, Elaine Antonella Cláudia Araújo, Maitê Florença Carvalho";

        List<String> list1 = new LinkedList<>(Arrays.asList(input1.split("\\s*,\\s*")));
        Collections.sort(list1);

        System.out.println("\nNomes em ordem alfabética:");
        list1.forEach(System.out::println);

        Map<String, List<String>> genderGroup = new LinkedHashMap<>();
        genderGroup.put("Feminino", new ArrayList<>());
        genderGroup.put("Masculino", new ArrayList<>());

        String input2 = "Sophia Maria Cecília Maximo - Feminino, Martin Pedro Javier - Masculino, Eleanor Lamb - Feminino, Danilo Raul Heitor Mendes - Masculino, Mariane Marli Lindespoon - Feminino, Raul Rodrigo Caldeira - Masculino, Elaine Antonella Cláudia Araújo - Feminino, Maitê Florença Carvalho - Feminino";
        Map<String, List<String>> list2 = Arrays.stream(input2.split("\\s*,\\s*")).map(p -> p.split("\\s*-\\s*")).filter(parts -> parts.length == 2).collect(Collectors.groupingBy(parts -> parts[1].trim(), () -> genderGroup, Collectors.mapping(parts -> parts[0].trim(), Collectors.toList())));
        list2.values().forEach(Collections::sort);

        // Feminino
        System.out.println("\nMulheres:");
        list2.getOrDefault("Feminino", Collections.emptyList()).forEach(System.out::println);

        // Masculino
        System.out.println("\nHomens:");
        list2.getOrDefault("Masculino", Collections.emptyList()).forEach(System.out::println);
    }
}
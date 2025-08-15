import java.util.*;

public class Main {
    public static void main(String[] args) {
        String input1 = "Sophia Maria Cecília Maximo, Martin Pedro Javier, Eleanor Lamb, Danilo Raul Heitor Mendes, Mariane Marli Lindespoon, Raul Rodrigo Caldeira, Elaine Antonella Cláudia Araújo, Maitê Florença Carvalho";

        List<String> list1 = new LinkedList<>(Arrays.asList(input1.split("\\s*,\\s*")));
        Collections.sort(list1);

        System.out.println("\nNomes em ordem alfabética:");
        list1.forEach(System.out::println);
    }
}
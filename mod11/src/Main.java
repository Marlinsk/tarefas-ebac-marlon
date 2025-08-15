import java.util.*;

// Lists of names to copy to input:
// Sophia Maria Cecília Maximo, Martin Pedro Javier, Eleanor Lamb, Danilo Raul Heitor Mendes, Mariane Marli Lindespoon, Raul Rodrigo Caldeira, Elaine Antonella Cláudia Araújo, Maitê Florença Carvalho

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite uma lista de nomes separados por vírgula e depois digite enter: ");
        String input  = scanner.nextLine();

        List<String> list1 = new LinkedList<>(Arrays.asList(input.split("\\s*,\\s*")));
        Collections.sort(list1);

        System.out.println("\nNomes em ordem alfabética:");
        list1.forEach(System.out::println);
    }
}
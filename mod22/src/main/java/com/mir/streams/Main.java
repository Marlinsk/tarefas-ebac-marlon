package main.java.com.mir.streams;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite uma lista de nomes e sexo separados por vírgula (ex: Ana-F, João-M, Pedro-M): ");
        String input = scanner.nextLine();

        List<String> pessoas = Arrays.asList(input.split("\\s*,\\s*"));

        List<Map.Entry<String, String>> listaProcessada = pessoas.stream().sorted()
                .map(p -> p.split("\\s*-\\s*"))
                .filter(parts -> parts.length == 2)
                .map(parts -> new AbstractMap.SimpleEntry<>(parts[0].trim(), parts[1].trim().toUpperCase()))
                .collect(Collectors.toList());

        System.out.println("\nLista processada:");
        listaProcessada.forEach(entry -> System.out.println(String.format("Nome: %s, Sexo: %s", entry.getKey(), entry.getValue())));

        Map<String, List<String>> agrupadoPorSexo = listaProcessada.stream()
                .filter(entry -> "M".equals(entry.getValue()) || "F".equals(entry.getValue()))
                .collect(Collectors.groupingBy(entry -> "M".equals(entry.getValue()) ? "Homens" : "Mulheres", Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

        System.out.println("\nAgrupado por sexo:");
        agrupadoPorSexo.forEach((sexo, nomes) -> System.out.println(sexo + ": " + nomes));
    }
}
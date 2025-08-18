package main.java.com.projeto1;

import main.java.com.projeto1.dao.ClienteMapDao;
import main.java.com.projeto1.domain.ClientePessoaFisica;
import main.java.com.projeto1.domain.ClientePessoaJuridica;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteMapDao dao = new ClienteMapDao();

        while (true) {
            System.out.println("\n=== MENU CLIENTES ===");
            System.out.println("1 - Cadastrar novo cliente");
            System.out.println("2 - Editar cliente");
            System.out.println("3 - Listar todos");
            System.out.println("4 - Remover cliente");
            System.out.println("5 - Buscar PF por CPF");
            System.out.println("6 - Buscar PJ por CNPJ");
            System.out.println("7 - Listar apenas PF");
            System.out.println("8 - Listar apenas PJ");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    dao.cadastrar();
                    break;

                case "2":
                    dao.editarPorDocumento();
                    break;

                case "3":
                    System.out.println("--- Lista de clientes ---");
                    dao.listarTodos().forEach(System.out::println);
                    break;

                case "4":
                    dao.removerPorDocumento();
                    break;

                case "5":
                    System.out.print("Digite o CPF (formato com pontuação): ");
                    String cpf = scanner.nextLine();
                    Optional<ClientePessoaFisica> pf = dao.buscarPfPorCpf(cpf);
                    System.out.println(pf.orElse(null));
                    break;

                case "6":
                    System.out.print("Digite o CNPJ (formato com pontuação): ");
                    String cnpj = scanner.nextLine();
                    Optional<ClientePessoaJuridica> pj = dao.buscarPjPorCnpj(cnpj);
                    System.out.println(pj.orElse(null));
                    break;

                case "7":
                    System.out.println("--- Pessoas Físicas ---");
                    dao.listarPessoasFisicas().forEach(System.out::println);
                    break;

                case "8":
                    System.out.println("--- Pessoas Jurídicas ---");
                    dao.listarPessoasJuridicas().forEach(System.out::println);
                    break;

                case "0":
                    System.out.println("Saindo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
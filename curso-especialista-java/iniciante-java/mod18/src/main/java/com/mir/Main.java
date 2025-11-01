package main.java.com.mir;

import main.java.com.mir.annotations.Tabela;
import main.java.com.mir.domain.Cliente;

public class Main {
    public static void main(String[] args) {
        Class<Cliente> classeCliente = Cliente.class;

        if (classeCliente.isAnnotationPresent(Tabela.class)) {
            Tabela tabela = classeCliente.getAnnotation(Tabela.class);
            System.out.println("Nome da tabela: " + tabela.nome());
        } else {
            System.out.println("A classe não possui a anotação @Tabela.");
        }
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Demonstração do HashMap Customizado ===\n");

        MyHashMap map = new MyHashMap();

        System.out.println("--- Teste 1: Adicionando elementos ---");
        map.put(1, 100);
        map.put(2, 200);
        map.put(3, 300);
        map.put(17, 400);  // Esta chave terá colisão com a chave 1 (17 % 16 = 1)
        map.put(33, 500);  // Esta chave terá colisão com a chave 1 (33 % 16 = 1)

        map.printAll();

        System.out.println("--- Teste 2: Buscando elementos ---");
        System.out.println("get(1) = " + map.get(1));
        System.out.println("get(2) = " + map.get(2));
        System.out.println("get(17) = " + map.get(17));
        System.out.println("get(99) = " + map.get(99) + " (não existe)\n");

        System.out.println("--- Teste 3: Atualizando valor ---");
        map.put(2, 999);
        System.out.println("get(2) após atualização = " + map.get(2) + "\n");

        System.out.println("--- Teste 4: Removendo elementos ---");
        map.delete(3);
        map.delete(17);
        map.delete(99);  // Tentativa de remover chave inexistente
        System.out.println();

        map.printAll();

        System.out.println("--- Teste 5: Adicionando mais elementos ---");
        map.put(4, 444);
        map.put(5, 555);
        map.put(6, 666);
        map.printAll();

        System.out.println("--- Teste 6: Limpando o mapa ---");
        map.clear();
        map.printAll();

        System.out.println("--- Teste 7: Adicionando após limpar ---");
        map.put(10, 1000);
        map.put(20, 2000);
        map.printAll();

        System.out.println("=== Fim da demonstração ===");
    }
}
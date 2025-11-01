/**
 * Implementação customizada de um HashMap do zero.
 *
 * JUSTIFICATIVA DA IMPLEMENTAÇÃO:
 * -------------------------------
 * Esta classe implementa uma estrutura de dados HashMap (tabela de dispersão/hash)
 * utilizando a técnica de ENCADEAMENTO (Chaining) para resolver colisões.
 *
 * CONCEITOS E ESTRUTURAS DE DADOS UTILIZADOS:
 *
 * 1. ARRAY DE BUCKETS:
 *    - Um array de HashNode[] serve como a estrutura base
 *    - Cada posição do array é chamada de "bucket"
 *    - A capacidade inicial é 16 (potência de 2, comum em implementações de HashMap)
 *
 * 2. FUNÇÃO HASH:
 *    - Transforma uma chave em um índice do array
 *    - Usa o operador módulo (%) para garantir que o índice está dentro dos limites
 *    - Math.abs() garante que valores negativos sejam tratados corretamente
 *    - Fórmula: index = Math.abs(key % buckets.length)
 *
 * 3. RESOLUÇÃO DE COLISÕES POR ENCADEAMENTO (CHAINING):
 *    - Quando duas chaves mapeiam para o mesmo índice, criamos uma lista encadeada
 *    - Cada bucket pode conter múltiplos elementos ligados através de ponteiros 'next'
 *    - Vantagem: simples de implementar e não há limite de elementos
 *    - Desvantagem: performance degrada se houver muitas colisões (O(n) no pior caso)
 *
 * 4. COMPLEXIDADE TEMPORAL:
 *    - put(): O(1) no caso médio, O(n) no pior caso (muitas colisões)
 *    - get(): O(1) no caso médio, O(n) no pior caso (muitas colisões)
 *    - delete(): O(1) no caso médio, O(n) no pior caso (muitas colisões)
 *    - clear(): O(1) (apenas recria o array)
 *
 * 5. COMPLEXIDADE ESPACIAL:
 *    - O(n) onde n é o número de elementos armazenados
 *
 * ALTERNATIVAS NÃO IMPLEMENTADAS (mas que existem em HashMaps profissionais):
 * - Rehashing: redimensionar o array quando o fator de carga é alto
 * - Open Addressing: armazenar elementos em outras posições do array
 * - Árvores balanceadas: Java 8+ usa árvores quando listas ficam muito grandes
 */
public class MyHashMap {
    private static final int INITIAL_CAPACITY = 16; // Capacidade inicial (potência de 2)
    private HashNode[] buckets;                      // Array de buckets (cada bucket é uma lista encadeada)
    private int size;                                // Número de elementos armazenados

    /**
     * Construtor: inicializa o HashMap com capacidade inicial de 16 buckets.
     */
    public MyHashMap() {
        this.buckets = new HashNode[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * FUNÇÃO HASH: Calcula o índice do bucket para uma chave.
     *
     * JUSTIFICATIVA:
     * - Usa o operador módulo (%) para distribuir as chaves entre os buckets
     * - Math.abs() trata chaves negativas corretamente
     * - Garante que o índice retornado está sempre entre 0 e (buckets.length - 1)
     *
     * EXEMPLO:
     * - key = 1  -> hash(1)  = 1 % 16 = 1
     * - key = 17 -> hash(17) = 17 % 16 = 1  (COLISÃO!)
     * - key = 33 -> hash(33) = 33 % 16 = 1  (COLISÃO!)
     */
    private int hash(int key) {
        return Math.abs(key % buckets.length);
    }

    /**
     * Adiciona o par chave/valor ao mapa.
     * Se a chave já existir, atualiza o valor.
     *
     * JUSTIFICATIVA DO ALGORITMO:
     * 1. Calcula o índice usando a função hash
     * 2. Percorre a lista encadeada no bucket procurando a chave
     * 3. Se a chave existe, atualiza o valor (não adiciona duplicata)
     * 4. Se a chave não existe, insere no INÍCIO da lista (mais eficiente - O(1))
     *
     * POR QUE INSERIR NO INÍCIO?
     * - Inserir no início é O(1) (não precisa percorrer a lista)
     * - Inserir no final seria O(n) (precisaria percorrer toda a lista)
     * - A ordem dos elementos na lista não importa para a funcionalidade do HashMap
     *
     * @param key   A chave do elemento
     * @param value O valor associado à chave
     */
    public void put(int key, int value) {
        int index = hash(key);
        HashNode head = buckets[index];

        // Verifica se a chave já existe no bucket (percorre a lista encadeada)
        HashNode current = head;
        while (current != null) {
            if (current.getKey() == key) {
                // Atualiza o valor se a chave já existir
                current.setValue(value);
                return;
            }
            current = current.getNext();
        }

        // Se a chave não existe, adiciona um novo nó no INÍCIO da lista
        HashNode newNode = new HashNode(key, value);
        newNode.setNext(head);      // O novo nó aponta para o antigo primeiro nó
        buckets[index] = newNode;   // O novo nó se torna o primeiro da lista
        size++;

        System.out.println("Adicionado: [" + key + " -> " + value + "]");
    }

    /**
     * Remove o valor associado à chave do mapa.
     *
     * JUSTIFICATIVA DO ALGORITMO:
     * 1. Calcula o índice usando a função hash
     * 2. Percorre a lista encadeada procurando a chave
     * 3. Mantém referência ao nó anterior (previous) para poder reajustar os ponteiros
     * 4. Remove o nó da lista reajustando os ponteiros
     *
     * CASOS DE REMOÇÃO:
     * A) Remover o primeiro nó: buckets[index] = current.getNext()
     * B) Remover nó do meio/final: previous.setNext(current.getNext())
     *
     * ANALOGIA: É como remover um elo de uma corrente
     * - Se remover o primeiro elo: a corrente começa no segundo elo
     * - Se remover um elo do meio: conectar o elo anterior ao próximo
     *
     * @param key A chave do elemento a ser removido
     * @return true se o elemento foi removido, false caso contrário
     */
    public boolean delete(int key) {
        int index = hash(key);
        HashNode head = buckets[index];
        HashNode previous = null;  // Mantém referência ao nó anterior
        HashNode current = head;   // Nó atual sendo analisado

        // Percorre a lista encadeada procurando a chave
        while (current != null) {
            if (current.getKey() == key) {
                // CASO A: Remove o nó da lista
                if (previous == null) {
                    // O nó a ser removido é o primeiro da lista
                    buckets[index] = current.getNext();
                } else {
                    // CASO B: O nó a ser removido está no meio ou no final
                    previous.setNext(current.getNext());
                }
                size--;
                System.out.println("Removido: chave " + key);
                return true;
            }
            previous = current;
            current = current.getNext();
        }

        System.out.println("Chave " + key + " não encontrada para remoção");
        return false;
    }

    /**
     * Retorna o valor associado à chave passada via parâmetro.
     *
     * JUSTIFICATIVA DO ALGORITMO:
     * 1. Calcula o índice usando a função hash (O(1))
     * 2. Percorre a lista encadeada no bucket procurando a chave (O(k) onde k = tamanho da lista)
     * 3. Retorna o valor se encontrar, ou -1 se não encontrar
     *
     * LIMITAÇÃO:
     * - Retorna -1 para chaves não encontradas (não distingue entre "não existe" e "valor = -1")
     * - Em implementações reais, usaríamos Optional<Integer> ou lançaríamos exceção
     *
     * @param key A chave do elemento
     * @return O valor associado à chave, ou -1 se não encontrado
     */
    public int get(int key) {
        int index = hash(key);
        HashNode head = buckets[index];
        HashNode current = head;

        // Percorre a lista encadeada procurando a chave
        while (current != null) {
            if (current.getKey() == key) {
                return current.getValue();
            }
            current = current.getNext();
        }

        // Retorna -1 se a chave não for encontrada
        return -1;
    }

    /**
     * Remove todos os elementos do mapa.
     *
     * JUSTIFICATIVA:
     * - Recria o array de buckets (descarta o array antigo)
     * - O Garbage Collector do Java limpa a memória dos objetos não referenciados
     * - Complexidade: O(1) - não precisa percorrer os elementos
     * - Alternativa seria percorrer e remover um por um (seria O(n), menos eficiente)
     */
    public void clear() {
        buckets = new HashNode[INITIAL_CAPACITY];
        size = 0;
        System.out.println("Mapa limpo! Todos os elementos foram removidos.");
    }

    /**
     * Retorna o número de elementos no mapa.
     * Métrica importante para calcular o fator de carga (load factor).
     */
    public int size() {
        return size;
    }

    /**
     * Verifica se o mapa está vazio.
     * Útil para validações e operações condicionais.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Imprime todos os elementos do mapa (método auxiliar para visualização).
     *
     * JUSTIFICATIVA:
     * - Percorre todos os buckets do array
     * - Para cada bucket não-nulo, percorre a lista encadeada
     * - Mostra visualmente como os elementos estão distribuídos
     * - Permite identificar colisões (buckets com múltiplos elementos)
     *
     * IMPORTANTE: Este método é apenas para debug/demonstração.
     * Em uma implementação profissional, implementaríamos Iterator ou toString().
     */
    public void printAll() {
        System.out.println("\n=== Conteúdo do HashMap ===");
        if (isEmpty()) {
            System.out.println("(vazio)");
            return;
        }

        // Percorre todos os buckets
        for (int i = 0; i < buckets.length; i++) {
            HashNode current = buckets[i];
            if (current != null) {
                System.out.print("Bucket[" + i + "]: ");
                // Percorre a lista encadeada no bucket
                while (current != null) {
                    System.out.print("[" + current.getKey() + " -> " + current.getValue() + "]");
                    if (current.getNext() != null) {
                        System.out.print(" -> ");  // Mostra o encadeamento
                    }
                    current = current.getNext();
                }
                System.out.println();
            }
        }
        System.out.println("Total de elementos: " + size);
        System.out.println("===========================\n");
    }
}

/**
 * Classe que representa um nó na estrutura de dados HashMap.
 *
 * JUSTIFICATIVA DA IMPLEMENTAÇÃO:
 * -------------------------------
 * Esta classe implementa um nó de lista encadeada (LinkedList Node) que é fundamental
 * para resolver COLISÕES em um HashMap através da técnica de ENCADEAMENTO (Chaining).
 *
 * CONCEITOS UTILIZADOS:
 *
 * 1. ENCAPSULAMENTO:
 *    - Os atributos são privados (private) para proteger o estado interno do objeto
 *    - O acesso é controlado através de métodos públicos (getters/setters)
 *    - Isso garante a integridade dos dados e evita modificações não autorizadas
 *
 * 2. LISTA ENCADEADA (LINKED LIST):
 *    - Cada nó mantém uma referência para o próximo nó (next)
 *    - Permite armazenar múltiplos valores no mesmo índice do array (bucket)
 *    - Resolve o problema de colisões quando duas chaves diferentes mapeiam para o mesmo índice
 *
 * 3. PAR CHAVE-VALOR:
 *    - Armazena um par (key, value) conforme o padrão de estruturas de mapeamento
 *    - A chave (key) é usada para localizar o valor
 *    - O valor (value) é o dado armazenado associado à chave
 *
 * EXEMPLO DE COLISÃO:
 * Se hash(1) = 1 e hash(17) = 1, ambos mapeiam para o mesmo bucket:
 * Bucket[1]: [17 -> 400] -> [1 -> 100] -> null
 *            (primeiro)     (segundo)
 */
public class HashNode {
    private int key;      // Chave para identificar o elemento
    private int value;    // Valor associado à chave
    private HashNode next; // Referência para o próximo nó (para encadeamento)

    /**
     * Construtor que inicializa um novo nó com chave e valor.
     * O ponteiro 'next' é inicializado como null.
     */
    public HashNode(int key, int value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    /**
     * Retorna a chave armazenada neste nó.
     * Necessário para buscar e comparar elementos na lista encadeada.
     */
    public int getKey() {
        return key;
    }

    /**
     * Retorna o valor armazenado neste nó.
     * Este é o dado que queremos recuperar quando buscamos pela chave.
     */
    public int getValue() {
        return value;
    }

    /**
     * Atualiza o valor armazenado neste nó.
     * Usado quando fazemos put() com uma chave já existente.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Retorna a referência para o próximo nó na lista encadeada.
     * Permite percorrer todos os elementos que colidiram no mesmo bucket.
     */
    public HashNode getNext() {
        return next;
    }

    /**
     * Define a referência para o próximo nó na lista encadeada.
     * Usado ao inserir ou remover elementos da lista.
     */
    public void setNext(HashNode next) {
        this.next = next;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ANALISE DE COMPLEXIDADE DE TEMPO E ESPACO ===\n");

        testStackComplexity();
        testQueueComplexity();
        testLinkedListComplexity();
    }

    private static void testStackComplexity() {
        System.out.println("ANALISE DE COMPLEXIDADE - STACK\n");
        testStackPush();
        testStackPop();
    }

    private static void testStackPush() {
        System.out.println("--- Stack.push() ---");

        int[] testSizes = {1000, 10000, 100000, 1000000};

        System.out.println("Tamanho\t\tTempo (ms)\tOps/ms");
        System.out.println("-------\t\t----------\t------");

        for (int size : testSizes) {
            Stack stack = new Stack();

            long startTime = System.nanoTime();
            for (int i = 0; i < size; i++) {
                stack.push(i);
            }
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            double opsPerMs = size / timeMs;

            System.out.printf("%,d\t\t%.3f\t\t%,.0f%n", size, timeMs, opsPerMs);
        }

        System.out.println("\nComplexidade de Tempo: O(1) amortizado*");
        System.out.println("  * A maioria das operacoes e O(1), mas quando o array esta cheio");
        System.out.println("    ocorre redimensionamento que e O(n), resultando em O(1) amortizado.");
        System.out.println("\nComplexidade de Espaco: O(n)");
        System.out.println("  Armazena n elementos + capacidade extra do array (dobra quando necessario).\n");
    }

    private static void testStackPop() {
        System.out.println("--- Stack.pop() ---");

        int[] testSizes = {1000, 10000, 100000, 1000000};

        System.out.println("Tamanho\t\tTempo (ms)\tOps/ms");
        System.out.println("-------\t\t----------\t------");

        for (int size : testSizes) {
            Stack stack = new Stack();

            for (int i = 0; i < size; i++) {
                stack.push(i);
            }

            long startTime = System.nanoTime();
            for (int i = 0; i < size; i++) {
                stack.pop();
            }
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            double opsPerMs = size / timeMs;

            System.out.printf("%,d\t\t%.3f\t\t%,.0f%n", size, timeMs, opsPerMs);
        }

        System.out.println("\nComplexidade de Tempo: O(1)");
        System.out.println("  Sempre remove do topo, apenas decrementa o indice.");
        System.out.println("\nComplexidade de Espaco: O(1)");
        System.out.println("  Nao aloca memoria adicional, apenas modifica o estado interno.\n");
    }

    private static void testQueueComplexity() {
        System.out.println("ANALISE DE COMPLEXIDADE - QUEUE\n");
        testQueueEnqueue();
        testQueueDequeue();
        testQueueFront();
        testQueueRear();
    }

    private static void testQueueEnqueue() {
        System.out.println("--- Queue.enqueue() ---");

        int[] testSizes = {1000, 10000, 100000, 1000000};

        System.out.println("Tamanho\t\tTempo (ms)\tOps/ms");
        System.out.println("-------\t\t----------\t------");

        for (int size : testSizes) {
            Queue queue = new Queue();

            long startTime = System.nanoTime();
            for (int i = 0; i < size; i++) {
                queue.enqueue(i);
            }
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            double opsPerMs = size / timeMs;

            System.out.printf("%,d\t\t%.3f\t\t%,.0f%n", size, timeMs, opsPerMs);
        }

        System.out.println("\nComplexidade de Tempo: O(1)");
        System.out.println("  Adiciona no rear, atualiza rear.next e ponteiro rear.");
        System.out.println("\nComplexidade de Espaco: O(1)");
        System.out.println("  Aloca apenas um novo Node por operacao.\n");
    }

    private static void testQueueDequeue() {
        System.out.println("--- Queue.dequeue() ---");

        int[] testSizes = {1000, 10000, 100000, 1000000};

        System.out.println("Tamanho\t\tTempo (ms)\tOps/ms");
        System.out.println("-------\t\t----------\t------");

        for (int size : testSizes) {
            Queue queue = new Queue();

            for (int i = 0; i < size; i++) {
                queue.enqueue(i);
            }

            long startTime = System.nanoTime();
            for (int i = 0; i < size; i++) {
                queue.dequeue();
            }
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            double opsPerMs = size / timeMs;

            System.out.printf("%,d\t\t%.3f\t\t%,.0f%n", size, timeMs, opsPerMs);
        }

        System.out.println("\nComplexidade de Tempo: O(1)");
        System.out.println("  Remove do front, apenas atualiza ponteiro front.");
        System.out.println("\nComplexidade de Espaco: O(1)");
        System.out.println("  Nao aloca memoria adicional.\n");
    }

    private static void testQueueFront() {
        System.out.println("--- Queue.front() ---");

        int[] testSizes = {1000, 10000, 100000, 1000000};
        int iterations = 100000;

        System.out.println("Tamanho Queue\tTempo (ms)\tOps/ms");
        System.out.println("-------------\t----------\t------");

        for (int size : testSizes) {
            Queue queue = new Queue();

            for (int i = 0; i < size; i++) {
                queue.enqueue(i);
            }

            long startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                queue.front();
            }
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            double opsPerMs = iterations / timeMs;

            System.out.printf("%,d\t\t%.3f\t\t%,.0f%n", size, timeMs, opsPerMs);
        }

        System.out.println("\nComplexidade de Tempo: O(1)");
        System.out.println("  Acesso direto ao front.data, independente do tamanho da fila.");
        System.out.println("\nComplexidade de Espaco: O(1)");
        System.out.println("  Nao aloca memoria adicional.\n");
    }

    private static void testQueueRear() {
        System.out.println("--- Queue.rear() ---");

        int[] testSizes = {1000, 10000, 100000, 1000000};
        int iterations = 100000;

        System.out.println("Tamanho Queue\tTempo (ms)\tOps/ms");
        System.out.println("-------------\t----------\t------");

        for (int size : testSizes) {
            Queue queue = new Queue();

            for (int i = 0; i < size; i++) {
                queue.enqueue(i);
            }

            long startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                queue.rear();
            }
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            double opsPerMs = iterations / timeMs;

            System.out.printf("%,d\t\t%.3f\t\t%,.0f%n", size, timeMs, opsPerMs);
        }

        System.out.println("\nComplexidade de Tempo: O(1)");
        System.out.println("  Acesso direto ao rear.data, independente do tamanho da fila.");
        System.out.println("\nComplexidade de Espaco: O(1)");
        System.out.println("  Nao aloca memoria adicional.\n");
    }

    private static void testLinkedListComplexity() {
        System.out.println("ANALISE DE COMPLEXIDADE - LINKEDLIST\n");
        testLinkedListPush();
        testLinkedListPop();
        testLinkedListInsert();
        testLinkedListRemove();
        testLinkedListElementAt();
    }

    private static void testLinkedListPush() {
        System.out.println("--- LinkedList.push() ---");

        int[] testSizes = {1000, 10000, 100000, 500000};

        System.out.println("Tamanho\t\tTempo (ms)\tOps/ms");
        System.out.println("-------\t\t----------\t------");

        for (int size : testSizes) {
            LinkedList list = new LinkedList();

            long startTime = System.nanoTime();
            for (int i = 0; i < size; i++) {
                list.push(new LinkedList.Node(i));
            }
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            double opsPerMs = size / timeMs;

            System.out.printf("%,d\t\t%.3f\t\t%,.0f%n", size, timeMs, opsPerMs);
        }

        System.out.println("\nComplexidade de Tempo: O(1)");
        System.out.println("  Adiciona no final (tail), apenas atualiza tail.next e ponteiro tail.");
        System.out.println("\nComplexidade de Espaco: O(1)");
        System.out.println("  Aloca apenas um novo Node por operacao.\n");
    }

    private static void testLinkedListPop() {
        System.out.println("--- LinkedList.pop() ---");

        int[] testSizes = {100, 500, 1000, 2000};

        System.out.println("Tamanho\t\tTempo (ms)\tOps/ms");
        System.out.println("-------\t\t----------\t------");

        for (int size : testSizes) {
            LinkedList list = new LinkedList();

            for (int i = 0; i < size; i++) {
                list.push(new LinkedList.Node(i));
            }

            long startTime = System.nanoTime();
            for (int i = 0; i < size; i++) {
                list.pop();
            }
            long endTime = System.nanoTime();

            double timeMs = (endTime - startTime) / 1_000_000.0;
            double opsPerMs = size / timeMs;

            System.out.printf("%,d\t\t%.3f\t\t%,.0f%n", size, timeMs, opsPerMs);
        }

        System.out.println("\nComplexidade de Tempo: O(n)");
        System.out.println("  Precisa percorrer a lista ate o penultimo no para remover o ultimo.");
        System.out.println("  Nao ha ponteiro 'previous', entao e necessario percorrer completamente.");
        System.out.println("\nComplexidade de Espaco: O(1)");
        System.out.println("  Nao aloca memoria adicional.\n");
    }

    private static void testLinkedListInsert() {
        System.out.println("--- LinkedList.insert(index, node) ---");

        int listSize = 10000;
        int[] insertPositions = {0, listSize / 4, listSize / 2, (3 * listSize) / 4, listSize};
        String[] positionNames = {"inicio", "25%", "meio", "75%", "fim"};

        System.out.println("Posicao\t\t\tTempo (µs)\tComplexidade");
        System.out.println("-------\t\t\t----------\t------------");

        for (int i = 0; i < insertPositions.length; i++) {
            LinkedList list = new LinkedList();

            for (int j = 0; j < listSize; j++) {
                list.push(new LinkedList.Node(j));
            }

            int insertPos = insertPositions[i];

            long startTime = System.nanoTime();
            list.insert(insertPos, new LinkedList.Node(9999));
            long endTime = System.nanoTime();

            double timeMicros = (endTime - startTime) / 1_000.0;

            System.out.printf("%s (indice %,d)\t%.3f\t\tO(%s)%n",
                positionNames[i], insertPos, timeMicros,
                insertPos == 0 || insertPos == listSize ? "1" : "n");
        }

        System.out.println("\nComplexidade de Tempo: O(n) no pior caso");
        System.out.println("  - O(1) para inserir no inicio (indice 0)");
        System.out.println("  - O(1) para inserir no fim (indice = size)");
        System.out.println("  - O(n) para inserir no meio, precisa percorrer ate o indice");
        System.out.println("\nComplexidade de Espaco: O(1)");
        System.out.println("  Aloca apenas um novo Node.\n");
    }

    private static void testLinkedListRemove() {
        System.out.println("--- LinkedList.remove(index) ---");

        int listSize = 10000;
        int[] removePositions = {0, listSize / 4, listSize / 2, (3 * listSize) / 4, listSize - 1};
        String[] positionNames = {"inicio", "25%", "meio", "75%", "fim"};

        System.out.println("Posicao\t\t\tTempo (µs)\tComplexidade");
        System.out.println("-------\t\t\t----------\t------------");

        for (int i = 0; i < removePositions.length; i++) {
            LinkedList list = new LinkedList();

            for (int j = 0; j < listSize; j++) {
                list.push(new LinkedList.Node(j));
            }

            int removePos = removePositions[i];

            long startTime = System.nanoTime();
            list.remove(removePos);
            long endTime = System.nanoTime();

            double timeMicros = (endTime - startTime) / 1_000.0;

            System.out.printf("%s (indice %,d)\t%.3f\t\tO(%s)%n",
                positionNames[i], removePos, timeMicros,
                removePos == 0 ? "1" : "n");
        }

        System.out.println("\nComplexidade de Tempo: O(n) no pior caso");
        System.out.println("  - O(1) para remover do inicio (indice 0)");
        System.out.println("  - O(n) para remover de outras posicoes, precisa percorrer ate indice-1");
        System.out.println("\nComplexidade de Espaco: O(1)");
        System.out.println("  Nao aloca memoria adicional.\n");
    }

    private static void testLinkedListElementAt() {
        System.out.println("--- LinkedList.elementAt(index) ---");

        int listSize = 100000;
        int[] accessPositions = {0, listSize / 4, listSize / 2, (3 * listSize) / 4, listSize - 1};
        String[] positionNames = {"inicio", "25%", "meio", "75%", "fim"};
        int iterations = 10000;

        System.out.println("Posicao\t\t\tTempo medio (µs)\tComplexidade");
        System.out.println("-------\t\t\t----------------\t------------");

        LinkedList list = new LinkedList();

        for (int j = 0; j < listSize; j++) {
            list.push(new LinkedList.Node(j));
        }

        for (int i = 0; i < accessPositions.length; i++) {
            int accessPos = accessPositions[i];

            long startTime = System.nanoTime();
            for (int j = 0; j < iterations; j++) {
                list.elementAt(accessPos);
            }
            long endTime = System.nanoTime();

            double avgTimeMicros = ((endTime - startTime) / 1_000.0) / iterations;

            System.out.printf("%s (indice %,d)\t%.3f\t\t\tO(%s)%n", positionNames[i], accessPos, avgTimeMicros, accessPos == 0 || accessPos == listSize - 1 ? "1" : "n");
        }

        System.out.println("\nComplexidade de Tempo: O(n) no pior caso");
        System.out.println("  - O(1) para acessar o inicio (head) - indice 0");
        System.out.println("  - O(1) para acessar o fim (tail) - indice size-1");
        System.out.println("  - O(n) para acessar o meio, precisa percorrer ate o indice");
        System.out.println("\nComplexidade de Espaco: O(1)");
        System.out.println("  Nao aloca memoria adicional.\n");
    }
}

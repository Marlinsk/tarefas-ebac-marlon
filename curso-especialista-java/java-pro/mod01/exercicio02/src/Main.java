public class Main {
    public static void main(String[] args) {
        Queue queue = new Queue();

        System.out.println("== Testing FIFO Queue Implementation ==\n");

        System.out.println("Is queue empty? " + queue.isEmpty());
        System.out.println("Queue size: " + queue.size());
        System.out.println();

        System.out.println("Enqueueing elements...");
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(40);
        queue.enqueue(50);
        System.out.println();

        queue.display();
        System.out.println("Queue size: " + queue.size());
        System.out.println();

        System.out.println("Analyzing queue positions:");
        System.out.println("Front element: " + queue.front());
        System.out.println("Rear element: " + queue.rear());
        System.out.println("Peek at front: " + queue.peek());
        System.out.println();

        System.out.println("Dequeueing elements...");
        System.out.println("Dequeued: " + queue.dequeue());
        System.out.println("Dequeued: " + queue.dequeue());
        System.out.println();

        queue.display();
        System.out.println("Queue size: " + queue.size());
        System.out.println("Front element: " + queue.front());
        System.out.println("Rear element: " + queue.rear());
        System.out.println();

        System.out.println("Enqueueing more elements...");
        queue.enqueue(60);
        queue.enqueue(70);
        System.out.println();

        queue.display();
        System.out.println("Queue size: " + queue.size());
        System.out.println("Front element: " + queue.front());
        System.out.println("Rear element: " + queue.rear());
        System.out.println();

        System.out.println("Dequeueing all remaining elements...");
        while (!queue.isEmpty()) {
            System.out.println("Dequeued: " + queue.dequeue());
        }
        System.out.println();

        System.out.println("Is queue empty? " + queue.isEmpty());
        queue.display();
    }
}
public class LinkedList {
    private Node head;
    private Node tail;
    private int size;

    public static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }

        public int getData() {
            return data;
        }

        public Node getNext() {
            return next;
        }
    }

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void push(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }

        node.next = null;

        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public Node pop() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }

        Node removedNode = tail;

        if (size == 1) {
            head = tail = null;
        } else {
            Node current = head;
            while (current.next != tail) {
                current = current.next;
            }
            current.next = null;
            tail = current;
        }

        size--;
        return removedNode;
    }

    public void insert(int index, Node node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == size) {
            push(node);
            return;
        }

        if (index == 0) {
            node.next = head;
            head = node;
            if (tail == null) {
                tail = node;
            }
            size++;
            return;
        }

        Node current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        node.next = current.next;
        current.next = node;
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
            size--;
            return;
        }

        Node current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        Node nodeToRemove = current.next;
        current.next = nodeToRemove.next;

        if (nodeToRemove == tail) {
            tail = current;
        }

        size--;
    }

    public Node elementAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            return head;
        }

        if (index == size - 1) {
            return tail;
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current;
    }

    public int size() {
        return size;
    }

    public void printList() {
        if (isEmpty()) {
            System.out.println("[]");
            return;
        }

        System.out.print("[");
        Node current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(", ");
            }
            current = current.next;
        }
        System.out.println("]");
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

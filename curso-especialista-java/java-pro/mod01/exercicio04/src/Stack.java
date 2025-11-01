public class Stack {
    private int[] elements;
    private int top;
    private int capacity;

    public Stack() {
        this(10);
    }

    public Stack(int capacity) {
        this.capacity = capacity;
        this.elements = new int[capacity];
        this.top = -1;
    }

    public void push(int element) {
        if (isFull()) {
            resize();
        }
        elements[++top] = element;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty! Cannot remove elements.");
        }
        return elements[top--];
    }

    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty! No elements to view.");
        }
        return elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == capacity - 1;
    }

    public int size() {
        return top + 1;
    }

    private void resize() {
        capacity *= 2;
        int[] newArray = new int[capacity];
        for (int i = 0; i <= top; i++) {
            newArray[i] = elements[i];
        }
        elements = newArray;
    }

    public void clear() {
        top = -1;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return;
        }
        System.out.print("Stack [bottom -> top]: ");
        for (int i = 0; i <= top; i++) {
            System.out.print(elements[i]);
            if (i < top) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}

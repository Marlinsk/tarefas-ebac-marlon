public class Main {
    public static void main(String[] args) {
        System.out.println("=== Testing Stack Implementation ===\n");

        Stack stack = new Stack(5);

        System.out.println("1. Adding elements to the stack:");
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);
        stack.display();
        System.out.println("Size: " + stack.size());

        System.out.println("\n2. Testing automatic resizing:");
        stack.push(60);
        stack.push(70);
        stack.display();
        System.out.println("Size: " + stack.size());

        System.out.println("\n3. Analyzing stack state (top, isEmpty, size):");
        System.out.println("Top element: " + stack.peek());
        System.out.println("Is empty? " + stack.isEmpty());
        System.out.println("Current size: " + stack.size());
        stack.display();

        System.out.println("\n4. Removing elements:");
        System.out.println("Removed: " + stack.pop());
        System.out.println("Removed: " + stack.pop());
        stack.display();
        System.out.println("Analyzing after removal:");
        System.out.println("  Top element: " + stack.peek());
        System.out.println("  Is empty? " + stack.isEmpty());
        System.out.println("  Current size: " + stack.size());

        System.out.println("\n5. Checking if empty:");
        System.out.println("Stack empty? " + stack.isEmpty());

        System.out.println("\n6. Removing all elements:");

        while (!stack.isEmpty()) {
            System.out.println("Removed: " + stack.pop());
        }

        System.out.println("Stack empty? " + stack.isEmpty());

        System.out.println("\n7. Testing operation on empty stack:");

        try {
            stack.pop();
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        System.out.println("\n8. Testing clear method:");
        stack.push(100);
        stack.push(200);
        stack.push(300);
        System.out.println("Before clearing:");
        stack.display();
        stack.clear();
        System.out.println("After clearing:");
        System.out.println("Stack empty? " + stack.isEmpty());
    }
}
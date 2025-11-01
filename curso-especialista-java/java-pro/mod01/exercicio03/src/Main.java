public class Main {
    public static void main(String[] args) {
        System.out.println("=== Testing LinkedList Implementation ===\n");

        LinkedList list = new LinkedList();

        System.out.println("1. Testing isEmpty on new list:");
        System.out.println("Is empty? " + list.isEmpty());
        System.out.println("Size: " + list.size());
        list.printList();
        System.out.println();

        System.out.println("2. Testing push() - Adding nodes to the end:");
        list.push(new LinkedList.Node(10));
        list.push(new LinkedList.Node(20));
        list.push(new LinkedList.Node(30));
        list.push(new LinkedList.Node(40));
        list.push(new LinkedList.Node(50));
        System.out.print("After pushing 10, 20, 30, 40, 50: ");
        list.printList();
        System.out.println("Size: " + list.size());
        System.out.println();

        System.out.println("3. Testing insert() - Adding node at specific positions:");
        list.insert(0, new LinkedList.Node(5));
        System.out.print("After inserting 5 at index 0: ");
        list.printList();
        list.insert(3, new LinkedList.Node(25));
        System.out.print("After inserting 25 at index 3: ");
        list.printList();
        list.insert(list.size(), new LinkedList.Node(60));
        System.out.print("After inserting 60 at the end: ");
        list.printList();
        System.out.println("Size: " + list.size());
        System.out.println();

        System.out.println("4. Testing elementAt() - Getting nodes by index:");
        System.out.println("Element at index 0: " + list.elementAt(0).getData());
        System.out.println("Element at index 3: " + list.elementAt(3).getData());
        System.out.println("Element at index " + (list.size() - 1) + ": " + list.elementAt(list.size() - 1).getData());
        System.out.println();

        System.out.println("5. Testing remove() - Removing nodes by index:");
        System.out.print("Before removing index 0: ");
        list.printList();
        list.remove(0);
        System.out.print("After removing index 0: ");
        list.printList();

        System.out.print("Before removing index 2: ");
        list.printList();
        list.remove(2);
        System.out.print("After removing index 2: ");
        list.printList();
        System.out.println("Size: " + list.size());
        System.out.println();

        System.out.println("6. Testing pop() - Removing and returning last node:");
        System.out.print("Before pop: ");
        list.printList();
        LinkedList.Node poppedNode = list.pop();
        System.out.println("Popped node value: " + poppedNode.getData());
        System.out.print("After pop: ");
        list.printList();
        System.out.println("Size: " + list.size());
        System.out.println();

        System.out.println("7. Testing printList() with various list states:");
        System.out.print("Current list: ");
        list.printList();

        while (list.size() > 1) {
            list.pop();
        }
        System.out.print("List with one element: ");
        list.printList();

        list.pop();
        System.out.print("Empty list: ");
        list.printList();
        System.out.println();

        System.out.println("8. Testing edge cases and exceptions:");

        // Test pop on empty list
        try {
            list.pop();
            System.out.println("ERROR: Should have thrown exception");
        } catch (RuntimeException e) {
            System.out.println("pop() on empty list: " + e.getMessage());
        }

        // Test remove on empty list
        try {
            list.remove(0);
            System.out.println("ERROR: Should have thrown exception");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("remove(0) on empty list: " + e.getMessage());
        }

        // Test elementAt on empty list
        try {
            list.elementAt(0);
            System.out.println("ERROR: Should have thrown exception");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("elementAt(0) on empty list: " + e.getMessage());
        }

        // Test insert with null node
        try {
            list.insert(0, null);
            System.out.println("ERROR: Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("insert(0, null): " + e.getMessage());
        }

        // Test push with null node
        try {
            list.push(null);
            System.out.println("ERROR: Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("push(null): " + e.getMessage());
        }

        System.out.println();
        System.out.println("9. Final test - Building a new list:");
        for (int i = 1; i <= 5; i++) {
            list.push(new LinkedList.Node(i * 10));
        }
        System.out.print("Final list: ");
        list.printList();
        System.out.println("Final size: " + list.size());

        System.out.println();
        System.out.println("=== All tests completed! ===");
    }
}
package data_structures;

public class StackOperations {
    public int[] stack;
    public int top;
    public int size;

    public StackOperations(int size) {
        this.size = size;
        this.stack = new int[size];
        this.top = -1;
    }

    public void push(int element) {
        if (top == size - 1) {
            System.out.println("Stack is full. Cannot push element " + element);
        } else {
            stack[++top] = element;
            System.out.println("=====> Element " + element + " pushed to stack");
        }
    }

    public void pop() {
        if (top == -1) {
            System.out.println("Stack is empty. Cannot pop element");
        } else {
            System.out.println("=====> Element " + stack[top--] + " popped from stack");
        }
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
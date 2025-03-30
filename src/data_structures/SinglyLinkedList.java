package data_structures;

public class SinglyLinkedList<T> {
    public Node<T> head;

    public SinglyLinkedList() {
        this.head = null;
    }

    public void insertAtLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void insertAtFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
    }

    public void insertBefore(T value, T newData) {
        Node<T> newNode = new Node<>(newData);
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        if (head.data.equals(value)) {
            newNode.next = head;
            head = newNode;
            return;
        }
        Node<T> temp = head;
        while (temp.next != null && !temp.next.data.equals(value)) {
            temp = temp.next;
        }
        if (temp.next != null) {
            newNode.next = temp.next;
            temp.next = newNode;
        } else {
            System.out.println("Value " + value + " not found in the list.");
        }
    }

    public void insertAfter(T value, T newData) {
        Node<T> newNode = new Node<>(newData);
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        Node<T> temp = head;
        while (temp != null && !temp.data.equals(value)) {
            temp = temp.next;
        }
        if (temp != null) {
            newNode.next = temp.next;
            temp.next = newNode;
        } else {
            System.out.println("Value " + value + " not found in the list.");
        }
    }

    public void deleteFirst() {
        if (head == null) {
            System.out.println("List is empty. No nodes to delete.");
            return;
        }
        head = head.next;
    }

    public void deleteLast() {
        if (head == null) {
            System.out.println("List is empty. No nodes to delete.");
            return;
        }
        if (head.next == null) {
            head = null;
            return;
        }
        Node<T> temp = head;
        while (temp.next.next != null) {
            temp = temp.next;
        }
        temp.next = null;
    }

    public void deleteByValue(T value) {
        if (head == null) {
            System.out.println("List is empty. No nodes to delete.");
            return;
        }
        if (head.data.equals(value)) {
            head = head.next;
            return;
        }
        Node<T> temp = head;
        while (temp.next != null && !temp.next.data.equals(value)) {
            temp = temp.next;
        }
        if (temp.next != null) {
            temp.next = temp.next.next;
        } else {
            System.out.println("Value " + value + " not found in the list.");
        }
    }

    public void display() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }
        Node<T> temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }
}
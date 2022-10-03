import java.util.Scanner;
import java.util.EmptyStackException;


class MyStack {
    static Scanner scan = new Scanner(System.in);

    private static class SNode {
        private Object element;
        private SNode next;

        SNode(Object element) {
            this.element = element;
        }
    }

    private SNode top;
    private int size;

    public MyStack() {
        top = null;
        size = 0;
    }

    public Object pop() {
        if (isEmpty()) throw new EmptyStackException();
        else {
            SNode q = top;
            top = top.next;
            size--;
            return q.element;
        }
    }

    public Object peek() {
        if (isEmpty()) throw new EmptyStackException();
        else return top.element;
    }

    public void push(Object element) {
        SNode node = new SNode(element);
        node.next = top;
        top = node;
        size++;
    }

    public boolean isEmpty() {
        return (top == null);
    }

    public int size() {
        return size;
    }
}
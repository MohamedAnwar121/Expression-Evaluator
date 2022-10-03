import java.util.Scanner;
import java.util.EmptyStackException;


interface IStack {
    Object pop();
    Object peek();
    void push(Object element);
    boolean isEmpty();
    int size();
}

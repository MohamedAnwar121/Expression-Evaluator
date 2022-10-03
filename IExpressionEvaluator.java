import java.util.Scanner;
import java.util.EmptyStackException;

interface IExpressionEvaluator {
    String infixToPostfix(String expression);
    int evaluate(String expression);
}

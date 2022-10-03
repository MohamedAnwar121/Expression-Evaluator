public class Evaluator implements IExpressionEvaluator {
    static Scanner scan = new Scanner(System.in);
    String tempExpression;
    String postfix = "";
    MyStack[] Stack = new MyStack[10];
    int count = 0;

    public Evaluator() {
        for (int i = 0; i < 10; i++) Stack[i] = new MyStack();
    }

    public String handlingErrors(String expression) {
        expression = expression.replaceAll(" ", "");
        checkBalance(expression);
        for (int j = 0; j < expression.length() - 1; j++) {
            if (expression.charAt(0) == '+') {
                expression = expression.substring(1);
            } else if (expression.charAt(j) == '-' && expression.charAt(j + 1) == '-' || expression.charAt(j) == '+' && expression.charAt(j + 1) == '+') {
                expression = expression.substring(0, j) + '+' + expression.substring(j + 2);
                if (j - 2 < 0) j--;
                else j -= 2;
            } else if ((expression.charAt(j) == '-' && expression.charAt(j + 1) == '+') || (expression.charAt(j) == '+' && expression.charAt(j + 1) == '-')) {
                expression = expression.substring(0, j) + '-' + expression.substring(j + 2);
                if (j - 2 < 0) j--;
                else j -= 2;
            } else if ((expression.charAt(j) == '-' || expression.charAt(j) == '+' || expression.charAt(j) == '*'
                    || expression.charAt(j) == '/' || expression.charAt(j) == '^') &&
                    (expression.charAt(j + 1) == '*' || expression.charAt(j + 1) == '/' || expression.charAt(j + 1) == '^')) {
                throw new RuntimeException();
            } else if ((expression.charAt(j) == '*' || expression.charAt(j) == '/' || expression.charAt(j) == '^') &&
                    (expression.charAt(j + 1) == '+')) {
                expression = expression.substring(0, j + 1) + expression.substring(j + 2);
            } else if (expression.charAt(j) >= '0' && expression.charAt(j) <= '9') {
                throw new RuntimeException();
            }
        }
        if( (expression.charAt(expression.length() - 1) >= '0' && expression.charAt(expression.length() - 1) <= '9')
                ||(expression.charAt(0) == '*' || expression.charAt(0) == '/' || expression.charAt(0) == '^')
                || expression.charAt(expression.length() - 1) == '-' || expression.charAt(expression.length() - 1) == '+'
                || expression.charAt(expression.length() - 1) == '*' || expression.charAt(expression.length() - 1) == '/'
                || expression.charAt(expression.length() - 1) == '^')
            throw new RuntimeException();
        return expression;
    }

    public void checkBalance(String expression) {
        MyStack checkStack = new MyStack();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') checkStack.push(expression.charAt(i));
            else if(expression.charAt(i) == ')') {
                if (checkStack.isEmpty()) throw new RuntimeException();
                else checkStack.pop();
            }
        }
        if(!checkStack.isEmpty()) throw new RuntimeException();
    }

    public int postfixHelper(int k) {
        MyStack tempStack = Stack[count];
        for (int i = k; i < tempExpression.length(); i++) {
            if (tempExpression.charAt(i) == 'a' || tempExpression.charAt(i) == 'b' || tempExpression.charAt(i) == 'c')
                postfix += tempExpression.charAt(i);
            else if (tempExpression.charAt(i) == ')') {
                while (!tempStack.isEmpty()) postfix += tempStack.pop();
                count--;
                return i;
            } else {
                if (tempExpression.charAt(i) == '(') {
                    count++;
                    i = postfixHelper(i + 1);
                } else {
                    if (tempStack.isEmpty()) tempStack.push(tempExpression.charAt(i));
                    else {
                        if (new Evaluator().checkPriority(tempExpression.charAt(i), (char) tempStack.peek()))
                            tempStack.push(tempExpression.charAt(i));
                        else {
                            for (int j = 0; !tempStack.isEmpty() && !new Evaluator().checkPriority(tempExpression.charAt(i), (char) tempStack.peek()); j++)
                                postfix += tempStack.pop();
                            tempStack.push(tempExpression.charAt(i));
                        }
                    }
                }
            }
        }
        int stSize = tempStack.size();
        for (int j = 0; j < stSize; j++) postfix += tempStack.pop();
        return 0;
    }

    public String infixToPostfix(String expression) {
        tempExpression = handlingErrors(expression);
        postfixHelper(0);
        return postfix;
    }

    public int evaluate(String expression) {
        MyStack stack = new MyStack();

        int a = scanInput();
        int b = scanInput();
        int c = scanInput();

        int o1, o2;
        for (int i = 0; i < expression.length(); i++) {
            o1 = 0;
            o2 = 0;
            if (expression.charAt(i) == 'a' || expression.charAt(i) == 'b' || expression.charAt(i) == 'c')
                stack.push(selectChar(expression.charAt(i), a, b, c));
            else {
                if (!stack.isEmpty()) o1 = (Integer) stack.pop();
                if (!stack.isEmpty()) o2 = (Integer) stack.pop();
                stack.push(evalHelper(expression.charAt(i), o1, o2));
            }
        }
        return (int) stack.pop();
    }

    public int selectChar(char temp, int a, int b, int c) {
        switch (temp) {
            case 'a':
                return a;
            case 'b':
                return b;
            case 'c':
                return c;
        }
        return 0;
    }

    public int evalHelper(char expression, int o1, int o2) {
        int eval = 0;
        switch (expression) {
            case '+':
                eval = o1 + o2;
                break;
            case '-':
                eval = o2 - o1;
                break;
            case '*':
                eval = o1 * o2;
                break;
            case '/':
                eval = o2 / o1;
                break;
            case '^':
                eval = (int) Math.pow(o2, o1);
        }
        return eval;
    }

    public boolean checkPriority(char a, char b) {
        return (a == '*' || a == '/' || a == '^') && (b == '+' || b == '-');
    }

    public int scanInput() {
        String ax = scan.next().replaceAll(" ", "");
        return Integer.parseInt(ax.substring(2));
    }

    public static void main(String[] args) {
        try {
            String postfix = new Evaluator().infixToPostfix(scan.nextLine());
            System.out.println(postfix);
            System.out.println(new Evaluator().evaluate(postfix));
        } catch (Exception e) {
            System.out.println("Error");
        }

    }
}
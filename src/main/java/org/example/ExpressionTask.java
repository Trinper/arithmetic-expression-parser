package org.example;

import java.util.Stack;

public class ExpressionTask implements Expression {
    private final char[] tokens;
    private final Stack<Character> operators;
    private final Stack<Double> values;
    public ExpressionTask(String str){
        this.tokens = str.toCharArray();
        operators = new Stack<>();
        values = new Stack<>();
        values.push(0.0);
    }
    @Override
    public String getText() {
        return null;
    }

    @Override
    public Double calculate() {

        boolean unaryMinus = true;

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ') {
                continue;
            }

            if (tokens[i] >= '0' && tokens[i] <= '9' || (tokens[i] == '-' && unaryMinus)) {
                StringBuilder sb = new StringBuilder();
                if (tokens[i] == '-' && unaryMinus) {
                    sb.append(tokens[i]);
                    i++;
                }
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.')) {
                    sb.append(tokens[i++]);
                }
                i--;
                values.push(Double.parseDouble(sb.toString()));
                unaryMinus = false;
            } else if (tokens[i] == '(') {
                operators.push(tokens[i]);
                unaryMinus = true;
            } else if (tokens[i] == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if ("+-*/sctle".indexOf(tokens[i]) != -1) {
                if (tokens[i] == 'c') i += 3;
                while (!operators.isEmpty() && hasPrecedence(tokens[i], operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(tokens[i]);
                unaryMinus = true;
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }
    private static boolean hasPrecedence(char op1, char op2) {
        return ("*/sctel".indexOf(op1) != -1 && "+-".indexOf(op2) != -1);

    }
    private static double applyOperation(char operator, double b, double a) {
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero is not allowed");
                }
                yield a / b;
            }
            case 's' -> Math.sin(b);
            case 'c' -> Math.cos(b);
            case 't' -> Math.tan(b);
            case 'e' -> Math.exp(b);
            case 'l' -> Math.log(b);
            default -> 0;
        };
    }
}


package com.example.arithmeticexpressionparser;

import java.util.Stack;

public class ExpressionTask implements Expression {
    private String expression;
    private final char[] tokens;
    private final Stack<Character> operators;
    private final Stack<Double> values;
    public ExpressionTask(String str){
        this.expression = str;
        this.tokens = getText().toCharArray();
        operators = new Stack<>();
        values = new Stack<>();
        values.push(0.0);
    }
    @Override
    public String getText() {
        expression = expression.trim();
        expression = expression.replaceAll("\\s+", " ");

        return this.expression;
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
        Operations operation = getOperation(operator);
        switch (operation) {
            case ADDITION:
                return a + b;
            case SUBTRACTION:
                return a - b;
            case MULTIPLICATION:
                return a * b;
            case DIVISION:
                if (b == 0) {
                    throw new ArithmeticException("Division by zero is not allowed");
                }
                return a / b;
            case SIN:
                return Math.sin(b);
            case COS:
                return Math.cos(b);
            case TAN:
                return Math.tan(b);
            case EXP:
                return Math.exp(b);
            case LN:
                return Math.log(b);
            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

    private static Operations getOperation(char operator) {
        switch (operator) {
            case '+':
                return Operations.ADDITION;
            case '-':
                return Operations.SUBTRACTION;
            case '*':
                return Operations.MULTIPLICATION;
            case '/':
                return Operations.DIVISION;
            case 's':
                return Operations.SIN;
            case 'c':
                return Operations.COS;
            case 't':
                return Operations.TAN;
            case 'e':
                return Operations.EXP;
            case 'l':
                return Operations.LN;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
}

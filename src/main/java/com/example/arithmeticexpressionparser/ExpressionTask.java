package com.example.arithmeticexpressionparser;

import java.util.Stack;

public class ExpressionTask implements Expression {
    private String expression;
    private final char[] tokens;
    private final Stack<Character> operators;
    private static final Stack<Double> values = new Stack<>();
    public ExpressionTask(String str){
        this.expression = str;
        this.tokens = getText().toCharArray();
        operators = new Stack<>();
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
                while (!operators.isEmpty() && hasPrecedence(tokens[i], operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(tokens[i]);
                if (tokens[i] == 'c') i += 2;
                unaryMinus = true;
            }
        }


        while (!operators.isEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }
    private static boolean hasPrecedence(char op1, char op2) {
        if ((op1 == 's' || op1 == 'c' || op1 == 't' || op1 == 'e' || op1 == 'l')) {
            return false;
        }
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }
    private static double applyOperation(char operator, double b, double a) {
        Operations operation = getOperation(operator);
        return switch (operation) {
            case ADDITION -> a + b;
            case SUBTRACTION -> a - b;
            case MULTIPLICATION -> a * b;
            case DIVISION -> {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero is not allowed");
                }
                yield a / b;
            }
            case SIN -> {
                values.push(a);
                yield Math.sin(b);
            }
            case COS -> {
                values.push(a);
                yield Math.cos(b);
            }
            case TAN -> {
                values.push(a);
                yield Math.tan(b);
            }
            case EXP -> {
                values.push(a);
                yield Math.exp(b);
            }
            case LN -> {
                values.push(a);
                yield Math.log(b);
            }
        };
    }

    private static Operations getOperation(char operator) {
        return switch (operator) {
            case '+' -> Operations.ADDITION;
            case '-' -> Operations.SUBTRACTION;
            case '*' -> Operations.MULTIPLICATION;
            case '/' -> Operations.DIVISION;
            case 's' -> Operations.SIN;
            case 'c' -> Operations.COS;
            case 't' -> Operations.TAN;
            case 'e' -> Operations.EXP;
            case 'l' -> Operations.LN;
            default -> throw new IllegalArgumentException("Invalid operator");
        };
    }
}
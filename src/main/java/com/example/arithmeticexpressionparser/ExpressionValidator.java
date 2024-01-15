package com.example.arithmeticexpressionparser;

import java.util.Stack;

public class ExpressionValidator {
    public static boolean isMathExpression(String expression){
        String[] tokens = expression.split("[+\\-*/()^\\s]+|sin|cos|tan|exp|ln");
        for (var token: tokens){
            if (!token.matches("-?\\d+(\\.\\d+)?") && !token.isEmpty()){
                return false;
            }
        }

        return true;
    }
    public static boolean isTrueMathExpression(String expression){
        Stack<Character> stack = new Stack<>();
        char prevChar = ' ';
        char prevNonSpaceChar = '0';
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            } else if ("+-/*".indexOf(c) != -1) {
                if ("+-/*".indexOf(prevNonSpaceChar) != -1 || (prevChar == '(' && "*/".indexOf(c) != -1)) {
                    return false;
                }
            }

            prevChar = c;
            prevNonSpaceChar = (c == ' ' ? prevNonSpaceChar : c);
        }

        return stack.isEmpty();
    }
}

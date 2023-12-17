package org.example;

import java.util.Arrays;
import java.util.Stack;

public class Main {
    public enum operations{
    }
    public static boolean isMathExpression(String expression){
       String[] tokens = expression.split("[+\\-*/()^\\s]+|sin|cos|tan|exp|ln");
       for (var token: tokens){
           if (!token.matches("-?\\d+(\\.\\d+)?") && !token.isEmpty()){
                return false;
           }
       }

       return isTrueMathExpression(expression);
    }
    private static boolean isTrueMathExpression(String expression){
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
            System.out.println(c);
            prevChar = c;
            prevNonSpaceChar = (c == ' ' ? prevNonSpaceChar : c);
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isMathExpression("1 / sin(2)"));
    }
}
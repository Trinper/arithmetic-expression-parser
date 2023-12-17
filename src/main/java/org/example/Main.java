package org.example;


import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public enum operations{
    }
    private static boolean isMathExpression(String expression){
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

            prevChar = c;
            prevNonSpaceChar = (c == ' ' ? prevNonSpaceChar : c);
        }

        return stack.isEmpty();
    }
    public static void textFileReader(FileReader fr, Scanner in, ArrayList<String> mathExpressions) {
        while(in.hasNextLine()){
            String line = in.nextLine();
            if (isMathExpression(line)){
                mathExpressions.add(line);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader(args[0]);
        FileWriter fw = new FileWriter("output.txt");
        Scanner in = new Scanner(fr);
        ArrayList<String> mathExpressions = new ArrayList<>();
        textFileReader(fr, in, mathExpressions);
        for(var expression: mathExpressions){
            fw.write((new ExpressionTask(expression).calculate()).toString() + '\n');
        }

        fw.close();
    }
}
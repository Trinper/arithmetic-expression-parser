package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

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
    public static void jsonFileReader(FileReader fr, ArrayList<String> mathExpressions) throws IOException, ParseException, JSONException {
        Object obj = new JSONParser().parse(fr);
        JSONObject jsonObject = new JSONObject(obj.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("expressions");
        for (int i = 0; i < jsonArray.length(); i++){
            if (isMathExpression((String) jsonArray.get(i))){
                mathExpressions.add((String) jsonArray.get(i));
            }
        }
    }
    public static void main(String[] args) throws IOException, JSONException, ParseException {
        if (args.length != 1){
            System.out.println("Pass the file path...");
            return;
        }

        String filePath = args[0];
        Path path = Paths.get(filePath);
        String fileType = null;
        try {
            fileType = Files.probeContentType(path);
        } catch (IOException e) {
            System.out.println("Error when trying to determine the file type..." + e.getMessage());
        }
        
        
        FileReader fr = new FileReader(args[0]);
        FileWriter fw = new FileWriter("output.txt");
        Scanner in = new Scanner(fr);
        ArrayList<String> mathExpressions = new ArrayList<>();

        switch (Objects.requireNonNull(fileType)){
            case "text/plain" ->  textFileReader(fr, in, mathExpressions);
            case "application/json" ->  jsonFileReader(fr, mathExpressions);
            default -> throw new IllegalStateException("Unexpected value: " + filePath);
        }
        
        for(var expression: mathExpressions){
            fw.write((new ExpressionTask(expression).calculate()).toString() + '\n');
        }

        fw.close();
    }
}
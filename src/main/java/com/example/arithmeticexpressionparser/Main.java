package com.example.arithmeticexpressionparser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Main extends Application {
    private static TextArea fileTextArea = new TextArea();
    private static TextArea ansTextArea = new TextArea();
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expression Task Parser");

        GridPane grid = new GridPane();
        grid.add(fileTextArea, 0, 0);
        grid.add(ansTextArea, 1, 0);


        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
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
    public static void textFileReader(FileReader fr, Scanner in, ArrayList<String> mathExpressions) {
        StringBuilder text  = new StringBuilder();
        while(in.hasNextLine()){
            String line = in.nextLine();
            text.append(line).append('\n');
            if (isMathExpression(line)){
                mathExpressions.add(line);
            }
        }
        fileTextArea.setText(text.toString());
    }
    public static void jsonFileReader(FileReader fr, ArrayList<String> mathExpressions) throws IOException, ParseException, JSONException {
        Object obj = new JSONParser().parse(fr);
        JSONObject jsonObject = new JSONObject(obj.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("expressions");
        for (int i = 0; i < jsonArray.length(); i++){
            String line = (String) jsonArray.get(i);
            if (isMathExpression(line)){
                fileTextArea.setText(line+ '\n');
                mathExpressions.add(line);
            }
        }
    }
    public static void xmlFileReader(String filePath, ArrayList<String> mathExpressions) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();
        parser.parse(new File(filePath), handler);
        mathExpressions.addAll(handler.getExpressions());
    }
    public static void main(String[] args) throws IOException, JSONException, ParseException, ParserConfigurationException, SAXException {
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
            case "text/xml" ->  xmlFileReader(filePath, mathExpressions);
            default -> throw new IllegalStateException("Unexpected value: " + filePath);
        }

        StringBuilder text  = new StringBuilder();
        for(var expression: mathExpressions){
            String ans = new ExpressionTask(expression).calculate().toString();
            text.append(ans).append('\n');
            fw.write(ans + '\n');
        }

        ansTextArea.setText(text.toString());

        fw.close();
        launch(args);
    }
}
package com.example.arithmeticexpressionparser;

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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Model {
    private static final ArrayList<String> expressions = new ArrayList<>();
    private final ArrayList<String> mathExpressions = new ArrayList<>();
    public ArrayList<String> getMathExpressions(){
        return this.mathExpressions;
    }
    public ArrayList<String> getExpressions(){
        return expressions;
    }
    public static void textFileReader(Scanner in, ArrayList<String> mathExpressions) {
        while(in.hasNextLine()){
            String line = in.nextLine();
            expressions.add(line);
            if (ExpressionValidator.isMathExpression(line) && ExpressionValidator.isTrueMathExpression(line)){
                mathExpressions.add(line);
            }
        }
    }
    public static void jsonFileReader(FileReader fr, ArrayList<String> mathExpressions) throws IOException, ParseException, JSONException {
        Object obj = new JSONParser().parse(fr);
        JSONObject jsonObject = new JSONObject(obj.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("expressions");
        for (int i = 0; i < jsonArray.length(); i++){
            String line = (String) jsonArray.get(i);
            expressions.add(line);
            if (ExpressionValidator.isMathExpression(line) && ExpressionValidator.isTrueMathExpression(line)){
                mathExpressions.add(line);
            }
        }
    }
    public static void xmlFileReader(String filePath, ArrayList<String> mathExpressions) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();
        parser.parse(new File(filePath), handler);
        expressions.addAll(handler.getExpressions());
        mathExpressions.addAll(handler.getMathExpressions());
    }

    Model(String args) throws IOException, JSONException, ParseException, ParserConfigurationException, SAXException {
        String filePath = args;
        Path path = Paths.get(filePath);
        String fileType = null;
        try {
            fileType = Files.probeContentType(path);
        } catch (IOException e) {
            System.out.println("Error when trying to determine the file type..." + e.getMessage());
        }

        switch (Objects.requireNonNull(fileType)) {
            case "application/vnd.rar" : filePath = ExtractFileFromArchive.unzipFile(args, "C:\\Java_code\\repos\\arithmetic-expression-parser\\inputs");
            case "application/x-zip-compressed" : filePath = ExtractFileFromArchive.unzipFile(args, "C:\\Java_code\\repos\\arithmetic-expression-parser\\inputs");
        }

        args = filePath;
        path = Paths.get(Objects.requireNonNull(filePath));
        try {
            fileType = Files.probeContentType(path);
        } catch (IOException e) {
            System.out.println("Error when trying to determine the file type..." + e.getMessage());
        }

        FileReader fr = new FileReader(args);
        Scanner in = new Scanner(fr);


        switch (Objects.requireNonNull(fileType)){
            case "text/plain" ->  textFileReader(in, mathExpressions);
            case "application/json" ->  jsonFileReader(fr, mathExpressions);
            case "text/xml" ->  xmlFileReader(filePath, mathExpressions);
            default -> throw new IllegalStateException("Unexpected value: " + filePath);
        }
    }
    public void write(String str) throws IOException {
        FileWriter fw = new FileWriter("output.txt");
        System.out.println(str);
        fw.write(str + '\n');
    }

}

package com.example.arithmeticexpressionparser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main extends Application {
    protected static final TextArea fileTextArea = new TextArea();
    protected static final TextArea ansTextArea = new TextArea();
    private static Controller controller;
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
    public static void main(String[] args) throws IOException, JSONException, ParseException, ParserConfigurationException, SAXException {
        if (args[0] == null){
            System.out.println("Pass the file path...");
            return;
        }

        controller = new Controller(args[0]);
        launch(args);
    }
}
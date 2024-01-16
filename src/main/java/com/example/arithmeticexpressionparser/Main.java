package com.example.arithmeticexpressionparser;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main extends Application {
    protected static final TextArea inputTextArea = new TextArea();
    protected static final TextArea fileTextArea = new TextArea();
    protected static final TextArea ansTextArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expression Task Parser");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        inputTextArea.setPrefRowCount(10);
        fileTextArea.setPrefRowCount(10);
        ansTextArea.setEditable(false);
        ansTextArea.setPrefRowCount(10);
        inputTextArea.setEditable(false);

        Label fileLabel = new Label("Input");
        Label ansLabel = new Label("Correct Expressions");
        Label resultLabel = new Label("Answer");

        grid.add(fileLabel, 0, 0);
        grid.add(inputTextArea, 0, 1);
        grid.add(ansLabel, 1, 0);
        grid.add(fileTextArea, 1, 1);
        grid.add(resultLabel, 2, 0);
        grid.add(ansTextArea, 2, 1);

        VBox root = new VBox(grid);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) throws IOException, JSONException, ParseException, ParserConfigurationException, SAXException {
        if (args[0] == null){
            System.out.println("Pass the file path...");
            return;
        }

        Controller controller = new Controller(args[0]);
        launch(args);
    }
}
module com.example.arithmeticexpressionparser {
    requires javafx.controls;
    requires javafx.fxml;
    requires android.json;
    requires json.simple;
    requires java.xml;


    opens com.example.arithmeticexpressionparser to javafx.fxml;
    exports com.example.arithmeticexpressionparser;
}
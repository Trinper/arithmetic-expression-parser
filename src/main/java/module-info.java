module com.example.arithmeticexpressionparser {
    requires javafx.controls;
    requires javafx.fxml;
    requires android.json;
    requires json.simple;
    requires java.xml;
    requires junrar;
    requires org.apache.commons.compress;


    opens com.example.arithmeticexpressionparser to javafx.fxml;
    exports com.example.arithmeticexpressionparser;
}
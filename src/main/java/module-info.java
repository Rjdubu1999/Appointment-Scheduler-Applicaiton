module com.example.wilkinson_c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.wilkinson_c195 to javafx.fxml;
    exports com.example.wilkinson_c195;
    exports Controller;
    opens Controller to javafx.fxml;
}
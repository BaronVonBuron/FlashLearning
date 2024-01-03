module com.example.flashlearning {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.flashlearning to javafx.fxml;
    exports com.example.flashlearning;
}
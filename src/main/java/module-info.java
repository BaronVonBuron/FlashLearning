module com.example.flashlearning {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jsoup;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.flashlearning to javafx.fxml;
    exports com.example.flashlearning;
}
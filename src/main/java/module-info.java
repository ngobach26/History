module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires com.google.gson;

    opens main to javafx.fxml;
    exports main;
    exports controller;
    opens controller to javafx.fxml;
    opens model to com.google.gson, javafx.base;
    exports controller.overview;
    opens controller.overview to javafx.fxml;
    exports controller.detail;
    opens controller.detail to javafx.fxml;
}

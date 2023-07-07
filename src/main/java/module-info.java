module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires com.google.gson;

    exports controller;
    exports main;
    exports controller.overview;
    exports controller.detail;

    opens main to javafx.fxml;
    opens controller to javafx.fxml;
    opens model to com.google.gson, javafx.base;
    opens controller.overview to javafx.fxml;
    opens controller.detail to javafx.fxml;
    opens services.HistorySearchService to com.google.gson, javafx.base;
    opens services.PageNavigationService to com.google.gson, javafx.base;
}

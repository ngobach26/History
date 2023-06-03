module main {
    requires javafx.controls;
    requires javafx.fxml;


    opens main to javafx.fxml;
    exports main;
    exports controller;
    opens controller to javafx.fxml;
}
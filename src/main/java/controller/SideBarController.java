package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.App;

import java.io.IOException;

public class SideBarController {

    @FXML
    void mainSwitch(ActionEvent event) throws IOException {
        App.setRoot("homepageView");
        App.stack.clear();
    }

    @FXML
    void figuresSwitch(ActionEvent event) throws IOException {
        App.setRoot("figureView");
        App.stack.clear();
    }

    @FXML
    void placeSwitch(ActionEvent event) throws IOException {
        App.setRoot("placeView");
        App.stack.clear();
    }

    @FXML
    void erasSwitch(ActionEvent event) throws IOException {
        App.setRoot("eraView");
        App.stack.clear();
    }

    @FXML
    void eventsSwitch(ActionEvent event) throws IOException {
        App.setRoot("eventView");
        App.stack.clear();
    }

    @FXML
    void festivalsSwitch(ActionEvent event) throws IOException {
        App.setRoot("festivalView");
        App.stack.clear();
    }

    @FXML
    void aboutusSwitch(ActionEvent event) throws IOException {
        App.setRoot("aboutUsView");
        App.stack.clear();
    }

    public void searchSwitch(ActionEvent actionEvent) {
    }

    public void exitSwitch(ActionEvent event) throws IOException {
        Platform.exit();
    }
}

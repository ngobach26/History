package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.App;

import java.io.IOException;

public class sideBarController {

    @FXML
    void mainSwitch(ActionEvent event) throws IOException {
        App.setRoot("homepageView");
    }

    @FXML
    void figuresSwitch(ActionEvent event) throws IOException {
        App.setRoot("figureView");

    }

    @FXML
    void placeSwitch(ActionEvent event) throws IOException {
        App.setRoot("placeView");
    }

    @FXML
    void erasSwitch(ActionEvent event) throws IOException {
        App.setRoot("eraView");
    }

    @FXML
    void eventsSwitch(ActionEvent event) throws IOException {
        App.setRoot("eventView");
    }

    @FXML
    void festivalsSwitch(ActionEvent event) throws IOException {
        App.setRoot("festivalView");
    }

    @FXML
    void aboutusSwitch(ActionEvent event) throws IOException {
        App.setRoot("aboutUsView");
    }

    public void searchSwitch(ActionEvent actionEvent) {
    }

    public void exitSwitch(ActionEvent event) throws IOException {
        Platform.exit();
    }
}

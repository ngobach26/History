package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class sideBarController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    public static void switchByGetFxml(String path, ActionEvent event) throws IOException {
    }

    @FXML
    void mainSwitch(ActionEvent event) throws IOException {
        switchByGetFxml("java/com/example/oopproject/view/homepageView.fxml", event);
    }

    @FXML
    void figuresSwitch(ActionEvent event) throws IOException {
        switchByGetFxml("resources/com/example/oopproject/view/figureView.fxml", event);
    }

    @FXML
    void placeSwitch(ActionEvent event) throws IOException {
        switchByGetFxml("resources/com/example/oopproject/view/placeView.fxml", event);
    }

    @FXML
    void erasSwitch(ActionEvent event) throws IOException {
        switchByGetFxml("resources/com/example/oopproject/view/eraView.fxml", event);
    }

    @FXML
    void eventsSwitch(ActionEvent event) throws IOException {
        switchByGetFxml("resources/com/example/oopproject/view/eventView.fxml", event);
    }

    @FXML
    void festivalsSwitch(ActionEvent event) throws IOException {
        switchByGetFxml("resources/com/example/oopproject/view/festivalView.fxml", event);
    }

    @FXML
    void aboutusSwitch(ActionEvent event) throws IOException {
        switchByGetFxml("resources/com/example/oopproject/view/AboutUsView.fxml", event);
    }

    public void exitSwitch(ActionEvent event) throws IOException {
        Platform.exit();
    }

    public void searchSwitch(ActionEvent actionEvent) {
    }
}

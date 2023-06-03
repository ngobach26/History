package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class figureDetailController {
    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("resources/com/example/oopproject/view/figureView.fxml", event);
    }
}

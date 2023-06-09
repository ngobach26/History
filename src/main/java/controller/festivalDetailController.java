package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import model.Festival;

public class festivalDetailController {
    @FXML
    private Text nameText;
    @FXML
    private Text dateText;
    @FXML
    private Text locationText;
    @FXML
    private Text firstTimeText;
    @FXML
    private Text noteText;
    @FXML
    private FlowPane relatedCharsFlowPane;

    @FXML
    public void onClickBack(ActionEvent festival) throws IOException {
        App.setRoot("festivalView");
    }

    public void setFestival(Festival festival){
        nameText.setText(festival.getName());
        dateText.setText(festival.getStartingDay());
        locationText.setText(festival.getLocation());
        firstTimeText.setText(festival.getFirstTime());
    }
}

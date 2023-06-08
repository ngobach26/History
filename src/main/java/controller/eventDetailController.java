package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import model.Event;

public class eventDetailController {
    @FXML
    private Text nameText;
    @FXML
    private Text timeText;
    @FXML
    private Text locationText;
    @FXML
    private Text overviewText;
    @FXML
    private Text causeText;
    @FXML
    private Text resultText;
    @FXML
    private FlowPane relatedCharsFlowPane;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        App.setRoot("eventView");
    }

    public void setEvent(Event event){
        nameText.setText(event.getName());
        timeText.setText(event.getTime());
        locationText.setText(event.getLocation());
        overviewText.setText(event.getDescription());
        locationText.setText(event.getCause());
        resultText.setText(event.getResult());
    }
}

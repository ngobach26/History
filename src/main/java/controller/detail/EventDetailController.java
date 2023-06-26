package controller.detail;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import model.Event;

public class EventDetailController {
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
        App.setAndReturnRoot("eventView");
    }

    public void setEvent(Event event){
        nameText.setText(event.getName());
        timeText.setText(event.getTime());
        locationText.setText(event.getLocation());
        overviewText.setText(event.getDescription());
        resultText.setText(event.getResult());
    }
    @FXML
    public void onDeleteInfo(){
        
    }
}

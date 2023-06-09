package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import model.Era;

public class eraDetailController {
    @FXML
    private Text nameText;
    @FXML
    private Text realnameText;
    @FXML
    private Text overviewText;
    @FXML
    private Text timeStampText;
    @FXML
    private Text homelandText;
    @FXML
    private Text founderText;
    @FXML
    private Text capLocateText;
    @FXML
    private Text timeText;
    @FXML
    private FlowPane kingsFlowPane;
    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        App.setRoot("eraView");
    }

    public void setEra(Era era){
        nameText.setText(era.getName());
    }
}

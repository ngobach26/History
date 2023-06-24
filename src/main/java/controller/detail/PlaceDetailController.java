package controller.detail;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import model.Relic;

public class PlaceDetailController {
    @FXML
    private Text nameText;
    @FXML
    private Text locationText;
    @FXML
    private Text constructionDateText;
    @FXML
    private Text founderText;
    @FXML
    private Text overviewText;
    @FXML
    private Text noteText;
    @FXML
    private Text categoryText;
    @FXML
    private Text approvedYearText;
    @FXML
    private FlowPane relatedFesFlowPane;
    @FXML
    private FlowPane relatedCharsFlowPane;

    @FXML
    public void onClickBack(ActionEvent relic) throws IOException {
        App.setRoot("placeView");
    }

    public void setRelic(Relic relic) {
        nameText.setText(relic.getName());
        locationText.setText(relic.getLocation());
        categoryText.setText(relic.getCategory());
        approvedYearText.setText(relic.getApprovedYear());
    }

    @FXML
    public void onDeleteInfo() {

    }
}

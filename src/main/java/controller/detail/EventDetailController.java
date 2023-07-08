package controller.detail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import main.EntityType;
import model.Event;
import model.HistoricalEntity;

public class EventDetailController implements DetailAction{
    @FXML
    private Text nameText;
    @FXML
    private Text timeText;
    @FXML
    private Text endtimeText;
    @FXML
    private Text locationText;
    @FXML
    private Text overviewText;
    @FXML
    private Text resultText;
    @FXML
    private FlowPane relatedCharsFlowPane;
    @FXML
    private FlowPane relatedEraFlowPane;
    private Event event;

    @Override
    public void setEntity(HistoricalEntity entity) {
        this.event = (Event) entity;
        nameText.setText(event.getName());
        timeText.setText(event.getStartYear());
        endtimeText.setText(event.getEndYear());
        locationText.setText(event.getLocation());
        overviewText.setText(event.getDescription());
        resultText.setText(event.getResult());

        FlowPaneUIHelp.populateEntity(event.getEras(), relatedEraFlowPane, EntityType.ERA.getName());

        FlowPaneUIHelp.populateEntity(event.getRelatedFigures(), relatedCharsFlowPane, EntityType.FIGURE.getName());
    }

    @FXML
    @Override
    public void onClickBack(ActionEvent event) {
        App.pageNavigationService.handleBackToPreviousPage(EntityPages.EVENT_PAGES.getViewPage());
    }

}

package controller.detail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import main.EntityType;
import model.Era;
import model.HistoricalEntity;

public class EraDetailController implements DetailAction{
    @FXML
    private Text nameText;
    @FXML
    private Text overviewText;
    @FXML
    private Text timeStampText;
    @FXML
    private Text timeStampEndText;
    @FXML
    private Text capLocateText;
    @FXML
    private FlowPane kingsFlowPane;
    @FXML
    private FlowPane eventsFlowPane;
    @FXML
    private FlowPane countryFlowPane;
    private Era era;

    @Override
    public void setEntity(HistoricalEntity entity) {
        this.era = (Era) entity;
        nameText.setText(era.getName());
        overviewText.setText(era.getDescription());
        timeStampText.setText(era.getStartYear());
        timeStampEndText.setText(era.getEndYear());
        capLocateText.setText(era.getCapital());

        for (String nation : era.getNationNames()) {
            Text nationText = new Text(nation);
            countryFlowPane.getChildren().add(nationText);
        }

        FlowPaneUIHelp.populateEntity(era.getRelatedEvents(), eventsFlowPane, EntityType.EVENT.getName());

        FlowPaneUIHelp.populateEntity(era.getKings(), kingsFlowPane, EntityType.FIGURE.getName());
    }

    @FXML
    @Override
    public void onClickBack(ActionEvent event) {
        App.pageNavigationService.handleBackToPreviousPage(EntityPages.ERA_PAGES.getViewPage());
    }

}

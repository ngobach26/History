package controller.detail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import main.EntityType;
import model.HistoricalEntity;
import model.Relic;

public class RelicDetailController implements DetailAction {
    @FXML
    private Text nameText;
    @FXML
    private Text locationText;
    @FXML
    private Text overviewText;
    @FXML
    private Text categoryText;
    @FXML
    private Text approvedYearText;
    @FXML
    private FlowPane relatedCharsFlowPane;
    @FXML
    private FlowPane relatedFesFlowPane;

    private Relic relic;

    @Override
    public void setEntity(HistoricalEntity entity) {
        this.relic = (Relic) entity;
        nameText.setText(relic.getName());
        locationText.setText(relic.getLocation());
        categoryText.setText(relic.getCategory());
        overviewText.setText(relic.getDescription());
        approvedYearText.setText(relic.getApprovedYear());

        FlowPaneUIHelp.populateEntity(relic.getRelatedFigures(), relatedCharsFlowPane, EntityType.FIGURE.getName());

        FlowPaneUIHelp.populateEntity(relic.getRelatedFestivals(), relatedFesFlowPane, EntityType.FESTIVAL.getName());
    }

    @FXML
    @Override
    public void onClickBack(ActionEvent event) {
        App.pageNavigationService.handleBackToPreviousPage(EntityPages.RELIC_PAGES.getViewPage());
    }
}

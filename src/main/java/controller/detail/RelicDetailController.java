package controller.detail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import main.EntityType;
import model.Relic;

public class RelicDetailController {
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

    public void setRelic(Relic relic) {
        nameText.setText(relic.getName());
        locationText.setText(relic.getLocation());
        categoryText.setText(relic.getCategory());
        overviewText.setText(relic.getDescription());
        approvedYearText.setText(relic.getApprovedYear());

        FlowPaneUIHelp.populateEntity(relic.getRelatedFigures(), relatedCharsFlowPane, EntityType.FIGURE.getName());

        FlowPaneUIHelp.populateEntity(relic.getRelatedFestivals(), relatedFesFlowPane, EntityType.FESTIVAL.getName());
    }

    @FXML
    public void onClickBack(ActionEvent event) {
        App.pageNavigationService.handleBackToPreviousPage(EntityPages.RELIC_PAGES.getViewPage());
    }

}

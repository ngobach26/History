package controller.detail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import main.EntityType;
import model.Festival;

public class FestivalDetailController {
    @FXML
    private Text nameText;
    @FXML
    private Text dateText;
    @FXML
    private Text locationText;
    @FXML
    private Text firstTimeText;
    @FXML
    private Text overviewText;
    @FXML
    private FlowPane relatedCharsFlowPane;
    @FXML
    private FlowPane relatedRelicFlowPane;

    public void setFestival(Festival festival) {
        nameText.setText(festival.getName());
        dateText.setText(festival.getStartingDay());
        overviewText.setText(festival.getDescription());
        locationText.setText(festival.getLocation());
        firstTimeText.setText(festival.getFirstTime());

        FlowPaneUIHelp.populateEntity(festival.getRelatedFigures(), relatedCharsFlowPane, EntityType.FIGURE.getName());

        FlowPaneUIHelp.populateEntity(festival.getRelatedRelics(), relatedRelicFlowPane, EntityType.RELIC.getName());
    }

    @FXML
    public void onClickBack(ActionEvent event) {
        App.pageNavigationService.handleBackToPreviousPage(EntityPages.FESTIVAL_PAGES.getViewPage());
    }

}

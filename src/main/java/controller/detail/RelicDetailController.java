package controller.detail;

import java.io.IOException;

import controller.helper.FlowPaneUIHelp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
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
    public void onClickBack(ActionEvent event) throws IOException {
        if(App.clickBackService.clickBackStack.isEmpty()){
            App.setAndReturnRoot(EntityPages.RELIC_PAGES.getViewPage());
        }else {
            App.clickBackService.handleBackToPreDetailPage();
        }
    }

    public void setRelic(Relic relic) {
        nameText.setText(relic.getName());
        locationText.setText(relic.getLocation());
        categoryText.setText(relic.getCategory());
        overviewText.setText(relic.getDescription());
        approvedYearText.setText(relic.getApprovedYear());

        FlowPaneUIHelp.populateFigure(relic.getRelatedCharsFlowPane(),relatedCharsFlowPane);
    }

    @FXML
    public void onDeleteInfo() {

    }
}

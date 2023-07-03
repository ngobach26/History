package controller.detail;

import java.io.IOException;

import helper.FlowPaneUIHelp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
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

    public void setFestival(Festival festival) {
        nameText.setText(festival.getName());
        dateText.setText(festival.getStartingDay());
        overviewText.setText(festival.getDescription());
        locationText.setText(festival.getLocation());
        firstTimeText.setText(festival.getFirstTime());

        FlowPaneUIHelp.populateFigure(festival.getRelatedFigures(), relatedCharsFlowPane);
    }

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        if(App.clickBackService.clickBackStack.isEmpty()){
            App.setAndReturnRoot(EntityPages.FESTIVAL_PAGES.getViewPage());
        }else {
            App.clickBackService.handleBackToPreDetailPage();
        }
    }

    public void onDeleteInfo() {

    }
}

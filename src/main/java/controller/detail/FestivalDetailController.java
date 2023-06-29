package controller.detail;

import java.io.IOException;

import controller.helper.FigureFlowPaneUIHelp;
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
    private Text noteText;
    @FXML
    private FlowPane relatedCharsFlowPane;

    public void setFestival(Festival festival) {
        nameText.setText(festival.getName());
        dateText.setText(festival.getStartingDay());
        locationText.setText(festival.getLocation());
        firstTimeText.setText(festival.getFirstTime());
        FigureFlowPaneUIHelp.populateFigure(festival.getRelatedFigures(), relatedCharsFlowPane);
    }

    @FXML
    public void onClickBack(ActionEvent festival) throws IOException {
        if(App.clickBackService.clickBackStack.isEmpty()){
            App.setAndReturnRoot(EntityPages.FESTIVAL_PAGES.getViewPage());
        }else {
            App.clickBackService.handleBackToPreDetailPage();
        }
    }

    public void onDeleteInfo() {

    }
}

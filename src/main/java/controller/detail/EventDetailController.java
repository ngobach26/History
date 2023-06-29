package controller.detail;

import java.io.IOException;

import controller.helper.FigureFlowPaneUIHelp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import model.Event;

public class EventDetailController {
    @FXML
    private Text nameText;
    @FXML
    private Text timeText;
    @FXML
    private Text locationText;
    @FXML
    private Text overviewText;
    @FXML
    private Text causeText;
    @FXML
    private Text resultText;
    @FXML
    private FlowPane relatedCharsFlowPane;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        if(App.clickBackService.clickBackStack.isEmpty()){
            App.setAndReturnRoot(EntityPages.EVENT_PAGES.getViewPage());
        }else {
            App.clickBackService.handleBackToPreDetailPage();
        }
    }

    public void setEvent(Event event){
        nameText.setText(event.getName());
        timeText.setText(event.getTime());
        locationText.setText(event.getLocation());
        overviewText.setText(event.getDescription());
        resultText.setText(event.getResult());
        FigureFlowPaneUIHelp.populateFigure(event.getRelatedFigures(), relatedCharsFlowPane);
    }
    @FXML
    public void onDeleteInfo(){
        
    }
}

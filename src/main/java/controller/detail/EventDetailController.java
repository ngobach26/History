package controller.detail;

import java.io.IOException;

import helper.FlowPaneUIHelp;
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

    public void setEvent(Event event){
        nameText.setText(event.getName());
        timeText.setText(event.getStartYear());
        endtimeText.setText(event.getEndYear());
        locationText.setText(event.getLocation());
        overviewText.setText(event.getDescription());
        resultText.setText(event.getResult());

        FlowPaneUIHelp.polulateEra(event.getEras(),relatedEraFlowPane,event);
        FlowPaneUIHelp.populateFigure(event.getRelatedFigures(), relatedCharsFlowPane,event);
    }

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        if(App.pageNavigationService.clickBackStack.isEmpty()){
            App.setAndReturnRoot(EntityPages.EVENT_PAGES.getViewPage());
        }else {
            App.pageNavigationService.handleBackToPreDetailPage();
        }
    }

    @FXML
    public void onDeleteInfo(){
        
    }
}

package controller.detail;

import helper.FlowPaneUIHelp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import main.EntityType;
import model.Era;

public class EraDetailController{
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

    public void setEra(Era era){
        nameText.setText(era.getName());
        overviewText.setText(era.getDescription());
        timeStampText.setText(era.getStartYear());
        timeStampEndText.setText(era.getEndYear());
        capLocateText.setText(era.getCapital());

        for (String nation : era.getNationNames()) {
            Text nationText = new Text(nation);
            countryFlowPane.getChildren().add(nationText);
        }

        FlowPaneUIHelp.populateEntity(era.getRelatedEvents(),eventsFlowPane,era, EntityType.EVENT);
        FlowPaneUIHelp.populateEntity(era.getKings(), kingsFlowPane,era,EntityType.FIGURE);
    }

    @FXML
    public void onClickBack(ActionEvent event){
        if(App.clickBackService.clickBackStack.isEmpty()){
                App.setRoot(EntityPages.ERA_PAGES.getViewPage());
        }else {
            App.clickBackService.handleBackToPreDetailPage();
        }
    }

}

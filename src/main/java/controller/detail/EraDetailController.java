package controller.detail;

import java.io.IOException;

import controller.helper.FigureFlowPaneUIHelp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import model.Era;

public class EraDetailController {
    @FXML
    private Text nameText;
    @FXML
    private Text realnameText;
    @FXML
    private Text overviewText;
    @FXML
    private Text timeStampText;
    @FXML
    private Text homelandText;
    @FXML
    private Text founderText;
    @FXML
    private Text capLocateText;
    @FXML
    private Text timeText;
    @FXML
    private FlowPane kingsFlowPane;
    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        if(App.clickBackService.clickBackStack.isEmpty()){
            App.setAndReturnRoot(EntityPages.ERA_PAGES.getViewPage());
        }else {
            App.clickBackService.handleBackToPreDetailPage();
        }
    }

    public void setEra(Era era){
        nameText.setText(era.getName());
        FigureFlowPaneUIHelp.populateFigure(era.getKings(), kingsFlowPane);
    }
    @FXML
    public void onDeleteInfo(){
        
    }
}

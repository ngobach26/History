package controller.detail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import main.EntityType;
import model.Figure;

import java.io.IOException;

import helper.FlowPaneUIHelp;

public class FigureDetailController{
    @FXML
    private Text nameText;

    @FXML
    private Text realNameText;

    @FXML
    private Text bornText;

    @FXML
    private Text diedText;

    @FXML
    private Text overviewText;

    @FXML
    private Text locText;

    @FXML
    private Text workTimeText;

     @FXML
     private FlowPane eraFlowPane;

    @FXML
    private FlowPane fatherFlowPane;

    @FXML
    private FlowPane motherFlowPane;

    @FXML
    private FlowPane childrenFlowPane;

    @FXML
    private FlowPane spouseFlowPane;

    @FXML
    private FlowPane aliasFlowPane;

    @FXML
    private FlowPane eventFlowPane;

    @FXML
    private FlowPane relicFlowPane;

    @FXML
    private FlowPane festivalFlowPane;



    public void setFigure(Figure figure) {

        nameText.setText(figure.getName());
        realNameText.setText(figure.getName());
        bornText.setText(figure.getBornYear());
        diedText.setText(figure.getDiedYear());
        overviewText.setText(figure.getDescription());
        workTimeText.setText(figure.getRole());
        locText.setText(figure.getLocation());


        for (String f : figure.getOtherNames()) {
            Text aliasText = new Text(f);
            aliasFlowPane.getChildren().add(aliasText);
        }

        FlowPaneUIHelp.populateEntity(figure.getFather(), fatherFlowPane,figure, EntityType.FIGURE);

        FlowPaneUIHelp.populateEntity(figure.getMother(), motherFlowPane,figure,EntityType.FIGURE);

        FlowPaneUIHelp.populateEntity(figure.getChildren(), childrenFlowPane,figure,EntityType.FIGURE);

        FlowPaneUIHelp.populateEntity(figure.getSpouses(), spouseFlowPane,figure,EntityType.FIGURE);

        FlowPaneUIHelp.populateEntity(figure.getRelatedEvents(), eventFlowPane,figure,EntityType.EVENT);

        FlowPaneUIHelp.populateEntity(figure.getRelatedRelics(), relicFlowPane,figure,EntityType.RELIC);

        FlowPaneUIHelp.populateEntity(figure.getRelatedFestivals(),festivalFlowPane,figure,EntityType.FESTIVAL);

        FlowPaneUIHelp.populateEntity(figure.getEras(), eraFlowPane,figure,EntityType.ERA);
    }

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        if(App.pageNavigationService.clickBackStack.isEmpty()){
            App.setAndReturnRoot(EntityPages.FIGURE_PAGES.getViewPage());
        }else {
            App.pageNavigationService.handleBackToPreDetailPage();
        }
    }



    @FXML
    public void onDeleteInfo() {

    }
}

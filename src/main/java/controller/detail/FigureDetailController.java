package controller.detail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import model.Figure;

import java.io.IOException;

import helper.FlowPaneUIHelp;

public class FigureDetailController implements DetailAction{
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

        FlowPaneUIHelp.populateFigure(figure.getFather(), fatherFlowPane);

        FlowPaneUIHelp.populateFigure(figure.getMother(), motherFlowPane);

        FlowPaneUIHelp.populateFigure(figure.getChildren(), childrenFlowPane);

        FlowPaneUIHelp.populateFigure(figure.getSpouses(), spouseFlowPane);

        FlowPaneUIHelp.populateEvent(figure.getRelatedEvents(), eventFlowPane);

        FlowPaneUIHelp.populateRelic(figure.getRelatedRelics(), relicFlowPane);

        FlowPaneUIHelp.polulateFestival(figure.getRelatedFestivals(),festivalFlowPane);

        FlowPaneUIHelp.polulateEra(figure.getEras(), eraFlowPane);
    }

    @FXML
    @Override
    public void onClickBack(ActionEvent event) throws IOException {
        if(App.clickBackService.clickBackStack.isEmpty()){
            App.setAndReturnRoot(EntityPages.FIGURE_PAGES.getViewPage());
        }else {
            App.clickBackService.handleBackToPreDetailPage();
        }
    }



    @FXML
    public void onDeleteInfo() {

    }
}

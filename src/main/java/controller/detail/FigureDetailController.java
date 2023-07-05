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

        FlowPaneUIHelp.populateFigure(figure.getFather(), fatherFlowPane,figure);

        FlowPaneUIHelp.populateFigure(figure.getMother(), motherFlowPane,figure);

        FlowPaneUIHelp.populateFigure(figure.getChildren(), childrenFlowPane,figure);

        FlowPaneUIHelp.populateFigure(figure.getSpouses(), spouseFlowPane,figure);

        FlowPaneUIHelp.populateEvent(figure.getRelatedEvents(), eventFlowPane,figure);

        FlowPaneUIHelp.populateRelic(figure.getRelatedRelics(), relicFlowPane,figure);

        FlowPaneUIHelp.polulateFestival(figure.getRelatedFestivals(),festivalFlowPane,figure);

        FlowPaneUIHelp.polulateEra(figure.getEras(), eraFlowPane,figure);
    }

    @FXML
    @Override
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

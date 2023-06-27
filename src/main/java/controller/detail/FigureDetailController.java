package controller.detail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import model.Figure;

import java.io.IOException;

import controller.helper.UIHelp;
import model.HistoricalEntity;

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

    // @FXML
    // private FlowPane eraFlowPane;

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

    public void setFigure(Figure figure) {
        nameText.setText(figure.getName());
        realNameText.setText(figure.getName());
        for (String f : figure.getOtherNames()) {
            Text aliasText = new Text(f);
            aliasFlowPane.getChildren().add(aliasText);
        }
        bornText.setText(figure.getBornYear());
        diedText.setText(figure.getDiedYear());
        overviewText.setText(figure.getDescription());
        UIHelp.populateFlowPane(figure.getFather(), fatherFlowPane);
        UIHelp.populateFlowPane(figure.getMother(), motherFlowPane);
        UIHelp.populateFlowPane(figure.getChildren(), childrenFlowPane);
        UIHelp.populateFlowPane(figure.getSpouses(), spouseFlowPane);
    }

    @FXML
    @Override
    public void onClickBack(ActionEvent event) throws IOException {
        if(App.clickBackService.clickBackStack.isEmpty()){
            App.setAndReturnRoot(EntityPages.FIGURE_PAGES.getViewPage());
        }else {
            App.clickBackService.handleClickBack();
        }

    }



    @FXML
    public void onDeleteInfo() {

    }
}

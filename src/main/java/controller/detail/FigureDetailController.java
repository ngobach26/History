package controller.detail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import model.Figure;

import java.io.IOException;

import helper.controllerUltil.UIUtils;
import model.HistoricalEntity;

public class FigureDetailController {
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

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        if(App.stack.isEmpty()){
            App.setRoot("figureView");
        }else{
            HistoricalEntity entity = App.stack.pop();
            FXMLLoader loader = App.setRoot("figureDetail");
            FigureDetailController controller = loader.getController();
            controller.setFigure((Figure) entity);
        }

    }

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
        // eraText.setText(figure.getEraString());
        UIUtils.populateFlowPane(figure.getFather(), fatherFlowPane);
        UIUtils.populateFlowPane(figure.getMother(), motherFlowPane);
        UIUtils.populateFlowPane(figure.getChildren(), childrenFlowPane);
        UIUtils.populateFlowPane(figure.getSpouses(), spouseFlowPane);
    }

    @FXML
    public void onDeleteInfo() {

    }
}

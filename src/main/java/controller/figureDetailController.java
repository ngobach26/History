package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import model.Figure;

import java.io.IOException;

public class figureDetailController {
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
    private Text workTimeText;

    @FXML
    private Text eraText;

    @FXML
    private Text fatherText;

    @FXML
    private Text motherText;

    @FXML
    private Text childrenText;

    @FXML
    private Text spouseText;
    @FXML 
    private FlowPane aliasFlowPane;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        App.setRoot("figureView");
    }

    public void setFigure(Figure figure) {
        nameText.setText(figure.getNames().get(0));
        realNameText.setText(figure.getNames().get(0));
        for (int i = 1; i < figure.getNames().size();i++) {
            Text aliasText = new Text(figure.getNames().get(i));
            aliasFlowPane.getChildren().add(aliasText);
        }
        bornText.setText(figure.getBornYear());
        diedText.setText(figure.getDiedYear());
        overviewText.setText(figure.getDescription());
        // workTimeText.setText(figure.get);
        eraText.setText(figure.getEraString());
        fatherText.setText(figure.getfatherString());
        motherText.setText(figure.getMotherString());

        spouseText.setText(figure.getSpouseString());
        childrenText.setText(figure.getChildrenString());
    }

}

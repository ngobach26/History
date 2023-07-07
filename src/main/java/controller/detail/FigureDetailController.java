package controller.detail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import main.App;
import main.EntityPages;
import main.EntityType;
import model.Figure;

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

        FlowPaneUIHelp.populateEntity(figure.getFather(), fatherFlowPane, EntityType.FIGURE.getName());

        FlowPaneUIHelp.populateEntity(figure.getMother(), motherFlowPane, EntityType.FIGURE.getName());

        FlowPaneUIHelp.populateEntity(figure.getChildren(), childrenFlowPane, EntityType.FIGURE.getName());

        FlowPaneUIHelp.populateEntity(figure.getSpouses(), spouseFlowPane, EntityType.FIGURE.getName());

        FlowPaneUIHelp.populateEntity(figure.getRelatedEvents(), eventFlowPane, EntityType.EVENT.getName());

        FlowPaneUIHelp.populateEntity(figure.getRelatedRelics(), relicFlowPane, EntityType.RELIC.getName());

        FlowPaneUIHelp.populateEntity(figure.getRelatedFestivals(), festivalFlowPane, EntityType.FESTIVAL.getName());

        FlowPaneUIHelp.populateEntity(figure.getEras(), eraFlowPane, EntityType.ERA.getName());
    }

    @FXML
    public void onClickBack(ActionEvent event) {
        App.pageNavigationService.handleBackToPreviousPage(EntityPages.FIGURE_PAGES.getViewPage());
    }

}

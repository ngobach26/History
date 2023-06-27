package controller.detail;

import java.io.IOException;
import java.util.Map;

import collection.FigureData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.App;
import model.Festival;
import model.Figure;

public class FestivalDetailController {
    @FXML
    private Text nameText;
    @FXML
    private Text dateText;
    @FXML
    private Text locationText;
    @FXML
    private Text firstTimeText;
    @FXML
    private Text noteText;
    @FXML
    private FlowPane relatedCharsFlowPane;

    @FXML
    public void onClickBack(ActionEvent festival) throws IOException {
        App.setAndReturnRoot("festivalView");
    }

    public void setFestival(Festival festival) {
        nameText.setText(festival.getName());
        dateText.setText(festival.getStartingDay());
        locationText.setText(festival.getLocation());
        firstTimeText.setText(festival.getFirstTime());
        for (Map.Entry<String, Integer> entry : festival.getRelatedFigures().entrySet()) {
            Text figureText = new Text(entry.getKey());
            if (entry.getValue() != null) {
                figureText.setFill(Color.web("#3498db"));
                figureText.setOnMouseClicked(mouseEvent -> {
                    Figure figure = FigureData.data.findName(entry.getKey());
                    try {
                        FXMLLoader loader = App.setAndReturnRoot("figureDetail");
                        FigureDetailController controller = loader.getController();
                        controller.setFigure(figure);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            relatedCharsFlowPane.getChildren().add(figureText);
        }
    }

    public void onDeleteInfo() {

    }
}

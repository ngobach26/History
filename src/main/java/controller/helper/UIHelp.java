package controller.helper;

import java.io.IOException;
import java.util.Map;
import collection.FigureData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.App;
import model.Figure;
import controller.detail.*;

public class UIHelp {
    
    public static void populateFlowPane(Map<String, Integer> figureMap, FlowPane flowPane) {
        for (Map.Entry<String, Integer> entry : figureMap.entrySet()) {
            Text figureText = new Text(entry.getKey());
            if (entry.getValue() != 0) {
                figureText.setFill(Color.web("#3498db"));
                figureText.setOnMouseClicked(mouseEvent -> {
                    Figure link = FigureData.data.getById(entry.getValue());
                    try {
                        FXMLLoader loader = App.setAndReturnRoot("figureDetail");
                        FigureDetailController controller = loader.getController();
                        controller.setFigure(link);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                figureText.setOnMouseEntered(mouseEvent -> {
                    figureText.setUnderline(true);
                    figureText.setCursor(Cursor.HAND);
                });
                figureText.setOnMouseExited(mouseEvent -> {
                    figureText.setUnderline(false);
                    figureText.setCursor(Cursor.DEFAULT);
                });
            }
            flowPane.getChildren().add(figureText);
        }

    }
    public static void setFigureClickHandler(TableView<Figure> tblFigure) {
        tblFigure.setRowFactory(tableView -> {
            TableRow<Figure> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Figure figure = row.getItem();
                    try {
                        FXMLLoader loader = App.setAndReturnRoot("figureDetail");
                        FigureDetailController controller = loader.getController();
                        controller.setFigure(figure);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

}

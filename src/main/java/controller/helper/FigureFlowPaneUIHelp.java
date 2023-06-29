package controller.helper;

import java.io.IOException;
import java.util.Map;
import collection.FigureData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.App;
import model.Figure;
import controller.detail.*;

public class FigureFlowPaneUIHelp {
    
    public static void populateFigure(Map<String, Integer> figureMap, FlowPane flowPane) {
        for (Map.Entry<String, Integer> entry : figureMap.entrySet()) {
            Text figureText = new Text(entry.getKey());
            int id = entry.getValue();

            if (isValidFigureId(id)) {
                setLinkStyle(figureText);
                figureText.setOnMouseClicked(mouseEvent -> navigateLink(id));
            }

            flowPane.getChildren().add(figureText);
        }

    }

    private static void setLinkStyle(Text textLink){
        textLink.setFill(Color.web("#3498db"));
        textLink.setOnMouseEntered(mouseEvent -> {
            textLink.setUnderline(true);
            textLink.setCursor(Cursor.HAND);
        });
        textLink.setOnMouseExited(mouseEvent -> {
            textLink.setUnderline(false);
            textLink.setCursor(Cursor.DEFAULT);
        });
    }

    private static void navigateLink(int id){
        Figure linkFigure = FigureData.data.getById(id);
        try {
            FXMLLoader loader = App.setAndReturnRoot("figureDetail");
            FigureDetailController controller = loader.getController();
            controller.setFigure(linkFigure);
            App.clickBackService.addEntityToClickBackStack(linkFigure);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidFigureId(int id){
        if(id <= 0) return false;
        if(id > FigureData.getNumOfFigure()) return false;
        return true;
    }


}

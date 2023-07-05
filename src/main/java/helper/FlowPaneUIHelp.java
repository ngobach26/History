package helper;

import java.io.IOException;
import java.util.Map;

import collection.*;
import javafx.scene.Cursor;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.App;
import model.*;
import main.EntityType;

public class FlowPaneUIHelp {

    public static void populateEntity(Map<String, Integer> entityMap, FlowPane flowPane, HistoricalEntity currentEntity, EntityType type) {
        if (entityMap == null) {
            return;
        }

        for (Map.Entry<String, Integer> entry : entityMap.entrySet()) {
            Text entityText = new Text(entry.getKey());
            int id = entry.getValue();

            if (isValidId(id)) {
                setLinkStyle(entityText);
                entityText.setOnMouseClicked(mouseEvent -> navigateLink(id, currentEntity, type));
            }

            flowPane.getChildren().add(entityText);
        }
    }

    private static void setLinkStyle(Text textLink) {
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

    private static void navigateLink(int id, HistoricalEntity entity, EntityType type) {
        try {
            App.pageNavigationService.addEntityToClickBackStack(entity);

            switch (type) {
                case ERA:
                    HandleDetailHelp.Era(EraData.data.getById(id));
                    break;
                case FESTIVAL:
                    HandleDetailHelp.Festival(FestivalData.data.getById(id));
                    break;
                case FIGURE:
                    HandleDetailHelp.Figure(FigureData.data.getById(id));
                    break;
                case EVENT:
                    HandleDetailHelp.Event(EventData.data.getById(id));
                    break;
                case RELIC:
                    HandleDetailHelp.Relic(RelicData.data.getById(id));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid entity type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidId(int id) {
        return id > 0;
    }
}
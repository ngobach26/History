package helper;

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

    /**
     * Tạo liên kết dựa trên entityMap được cung cấp.
     *
     * @param entityMap     Map chứa tên của các entity và các ID tương ứng
     * @param flowPane      FlowPane để chứa các liên kết
     * @param currentEntity entity hiện tại để lưu trữ vào stack nhằm việc quay lại trang đó
     * @param type          kiểu của entity trong entityMap
     */
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


    private static void navigateLink(int id, HistoricalEntity currentView, EntityType type) {
        try {
            App.clickBackService.addEntityToClickBackStack(currentView);
            // Khi chuyển sang trang khác thì sẽ push HistoricalEntity hiện tại vào stack
            switch (type) {
                case ERA:
                    HandleDetailHelp.Era(EraData.getEraCollection().getById(id));
                    break;
                case FESTIVAL:
                    HandleDetailHelp.Festival(FestivalData.getFestivalCollection().getById(id));
                    break;
                case FIGURE:
                    HandleDetailHelp.Figure(FigureData.getFigureCollection().getById(id));
                    break;
                case EVENT:
                    HandleDetailHelp.Event(EventData.getEventCollection().getById(id));
                    break;
                case RELIC:
                    HandleDetailHelp.Relic(RelicData.getRelicCollection().getById(id));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid entity type: " + type);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    private static boolean isValidId(int id) {
        return id > 0;
    }
}
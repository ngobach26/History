package controller.detail;

import java.util.Map;

import javafx.scene.Cursor;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.App;

public class FlowPaneUIHelp {

    /**
     * Tạo liên kết dựa trên entityMap được cung cấp.
     *
     * @param entityMap Map chứa tên của các entity và các ID tương ứng
     * @param flowPane  FlowPane để chứa các liên kết
     * @param type      kiểu của entity trong entityMap
     */
    public static void populateEntity(Map<String, Integer> entityMap, FlowPane flowPane,
                                      String type) {
        if (entityMap == null) {
            return;
        }

        for (Map.Entry<String, Integer> entry : entityMap.entrySet()) {
            Text entityText = new Text(entry.getKey());
            int id = entry.getValue();

            if (isValidId(id)) {
                setLinkStyle(entityText);
                entityText.setOnMouseClicked(mouseEvent -> navigateLink(id, type));
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

    private static void navigateLink(int id, String type) {
        App.pageNavigationService.handleDetailToDetail(id,type);
    }

    private static boolean isValidId(int id) {
        return id > 0;
    }
}
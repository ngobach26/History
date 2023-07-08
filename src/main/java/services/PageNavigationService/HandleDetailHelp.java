package services.PageNavigationService;

import controller.detail.*;
import javafx.fxml.FXMLLoader;
import main.App;
import main.EntityPages;
import model.*;

public class HandleDetailHelp {
    public static <T extends HistoricalEntity,C extends DetailAction> void handeDetail(HistoricalEntity entity, String pageLocation) {
        try {
            FXMLLoader loader = App.setAndReturnRoot(pageLocation);
            C controller = loader.getController();
            controller.setEntity((T) entity);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}

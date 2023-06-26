package controller.helper;

import controller.detail.*;
import javafx.fxml.FXMLLoader;
import main.App;
import main.EntityPages;
import model.*;

import java.io.IOException;

public class HandleDetailHelp {
    public static void Figure(HistoricalEntity entity) throws IOException {
        FXMLLoader loader = App.setAndReturnRoot(EntityPages.FIGURE_PAGES.getDetailPage());
        FigureDetailController controller = loader.getController();
        controller.setFigure((Figure) entity);
    }

    public static void Era(HistoricalEntity entity) throws IOException {
        FXMLLoader loader = App.setAndReturnRoot(EntityPages.ERA_PAGES.getDetailPage());
        EraDetailController controller = loader.getController();
        controller.setEra((Era) entity);
    }

    public static void Event(HistoricalEntity entity) throws IOException {
        FXMLLoader loader = App.setAndReturnRoot(EntityPages.EVENT_PAGES.getDetailPage());
        EventDetailController controller = loader.getController();
        controller.setEvent((Event) entity);
    }

    public static void Festival(HistoricalEntity entity) throws IOException {
        FXMLLoader loader = App.setAndReturnRoot(EntityPages.FESTIVAL_PAGES.getDetailPage());
        FestivalDetailController controller = loader.getController();
        controller.setFestival((Festival) entity);
    }

    public static void Relic(HistoricalEntity entity) throws IOException {
        FXMLLoader loader = App.setAndReturnRoot(EntityPages.RELIC_PAGES.getDetailPage());
        RelicDetailController controller = loader.getController();
        controller.setRelic((Relic) entity);
    }
}

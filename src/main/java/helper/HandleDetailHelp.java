package helper;

import controller.detail.*;
import javafx.fxml.FXMLLoader;
import main.App;
import main.EntityPages;
import model.*;

import java.io.IOException;

public class HandleDetailHelp {
    public static void Figure(HistoricalEntity entity){
        try{
            FXMLLoader loader = App.setAndReturnRoot(EntityPages.FIGURE_PAGES.getDetailPage());
            FigureDetailController controller = loader.getController();
            controller.setFigure((Figure) entity);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static void Era(HistoricalEntity entity){
        try{
            FXMLLoader loader = App.setAndReturnRoot(EntityPages.ERA_PAGES.getDetailPage());
            EraDetailController controller = loader.getController();
            controller.setEra((Era) entity);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static void Event(HistoricalEntity entity){
        try{
            FXMLLoader loader = App.setAndReturnRoot(EntityPages.EVENT_PAGES.getDetailPage());
            EventDetailController controller = loader.getController();
            controller.setEvent((Event) entity);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static void Festival(HistoricalEntity entity){
        try{
            FXMLLoader loader = App.setAndReturnRoot(EntityPages.FESTIVAL_PAGES.getDetailPage());
            FestivalDetailController controller = loader.getController();
            controller.setFestival((Festival) entity);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static void Relic(HistoricalEntity entity){
        try{
            FXMLLoader loader = App.setAndReturnRoot(EntityPages.RELIC_PAGES.getDetailPage());
            RelicDetailController controller = loader.getController();
            controller.setRelic((Relic) entity);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}

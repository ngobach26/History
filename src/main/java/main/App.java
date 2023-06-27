package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import collection.EraData;
import collection.EventData;
import collection.FestivalData;
import collection.FigureData;
import collection.RelicData;
import controller.services.CLickBackService;

public class App extends Application {
    private static Scene scene;
    public static CLickBackService clickBackService = new CLickBackService();


    @Override
    public void start(Stage stage) throws IOException {
        loadData();
        final String appName = "Vietnam History Wikiapp";
        scene = new Scene(loadFXML(StaticPages.HOME_PAGE.getUrl()).load(), 950, 750);
        scene.getStylesheets().add(getClass().getResource("/stylesheet/mainScreen.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle(appName);
        stage.show();
    }

    public static FXMLLoader setAndReturnRoot(String fxml) throws IOException {
        FXMLLoader loader = loadFXML(fxml);
        scene.setRoot(loader.load());
        return loader;
    }

    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/" + fxml + ".fxml"));
        return fxmlLoader;
    }

    private static void loadData() {
        FigureData.loadJson();
        EventData.loadJson();
        FestivalData.loadJson();
        EraData.loadJson();
        RelicData.loadJson();
    }

    public static void main(String[] args) {
        launch();
    }
}
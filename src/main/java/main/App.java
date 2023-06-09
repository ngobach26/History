package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import collection.EraCollection;
import collection.EventCollection;
import collection.FestivalCollection;
import collection.FigureCollection;
import collection.RelicCollection;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        loadData();
        final String appName = "Vietnam History Wikiapp";
        scene = new Scene(loadFXML("homepageView").load(), 950, 750);
        scene.getStylesheets().add(getClass().getResource("/stylesheet/mainScreen.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle(appName);
        stage.show();
    }

    public static FXMLLoader setRoot(String fxml) throws IOException {
        FXMLLoader loader = loadFXML(fxml);
        scene.setRoot(loader.load());
        return loader;
    }

    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/" + fxml + ".fxml"));
        return fxmlLoader;
    }

    private static void loadData(){
        FigureCollection.loadJson();
        EventCollection.loadJson();
        FestivalCollection.loadJson();
        EraCollection.loadJson();
        RelicCollection.loadJson();
    }

    public static void main(String[] args) {
        launch();
    }
}
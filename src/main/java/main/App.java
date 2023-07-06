package main;

import services.ClickBackService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import collection.EraData;
import collection.EventData;
import collection.FestivalData;
import collection.FigureData;
import collection.RelicData;

public class App extends Application {
    public static ClickBackService clickBackService = new ClickBackService();
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        loadData();
        final String appName = "Vietnam History Wikiapp";
        scene = new Scene(loadFXML(StaticPages.HOME_PAGE.getUrl()).load(), 950, 780);
        scene.getStylesheets().add(getClass().getResource("/stylesheet/mainScreen.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle(appName);
        Image icon = new Image(getClass().getResourceAsStream("/img/vn_pic.png"));
        stage.getIcons().add(icon);
        stage.show();
    }

    public static FXMLLoader setAndReturnRoot(String fxml) {
        FXMLLoader loader = null;
        try {
            loader = loadFXML(fxml);
            scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;// Ở đây có thể trả về null, nên cần phải xử lí NullPointerException khi gọi hàm này
    }

    public static void setRoot(String fxml){
        try{
            FXMLLoader loader = loadFXML(fxml);
            scene.setRoot(loader.load());
        }catch (IOException e){
            e.printStackTrace();
        }
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
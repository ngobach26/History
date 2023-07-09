package main;

import services.PageNavigationService.PageNavigationService;
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
import services.HistorySearchService.HistorySearchService;

public class App extends Application {
    public static PageNavigationService pageNavigationService = new PageNavigationService();
    public static HistorySearchService searchHistoryService = new HistorySearchService();
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        loadData();
        final String appName = "Vietnam History Wikiapp";
        scene = new Scene(loadFXML(OtherPages.HOME_PAGE.getUrl()).load(), 950, 650);
        scene.getStylesheets().add(getClass().getResource("/stylesheet/mainScreen.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle(appName);
        Image icon = new Image(getClass().getResourceAsStream("/img/vn_pic.png"));
        stage.getIcons().add(icon);
        stage.show();
        stage.setOnCloseRequest(event -> {
            searchHistoryService.writeJson();
        });
    }
    private static void loadData() {
        FigureData.loadJson();
        EventData.loadJson();
        FestivalData.loadJson();
        EraData.loadJson();
        RelicData.loadJson();
        searchHistoryService.loadJson();
    }

    /**
     * Đặt nút gốc cho scene bằng cách sử dụng tệp FXML được chỉ định và trả về đối
     * tượng FXMLLoader.
     *
     * @param fxml đường dẫn đến tệp FXML
     * @return đối tượng FXMLLoader được sử dụng để tải tệp FXML và đặt nút gốc
     * @throws IOException nếu xảy ra lỗi khi tải tệp FXML
     *
     * @exception NullPointerException cần xử lí lỗi này khi gọi hàm này
     */
    public static FXMLLoader setAndReturnRoot(String fxml) {
        FXMLLoader loader = null;
        try {
            loader = loadFXML(fxml);
            scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }

    /**
     * Thiết lập nút gốc của scene từ tệp FXML được chỉ định.
     *
     * @param fxml đường dẫn đến tệp FXML
     * @throws IOException nếu xảy ra lỗi khi tải tệp FXML
     *
     * @apiNote Phương thức này được sử dụng để tải tệp FXML và thiết lập nút gốc
     *          cho scene. Nếu không thể tải tệp FXML, phương thức sẽ in thông tin
     *          về lỗi ra đầu ra và tiếp tục thực thi. Không trả về giá trị.
     */
    public static void setRoot(String fxml) {
        try {
            FXMLLoader loader = loadFXML(fxml);
            scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/" + fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }
}
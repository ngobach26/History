package controller.overview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import collection.RelicCollection;
import controller.SearchBarController;
import controller.SearchBoxListener;
import controller.detail.PlaceDetailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
import model.Relic;

public class PlaceViewController implements Initializable{
    @FXML
    private TableView<Relic> siteTable;

    @FXML
    private TableColumn<Relic, Integer> colSiteId;
    @FXML
    private TableColumn<Relic, String> colSiteName;
    @FXML
    private TableColumn<Relic, String> colSiteDate;
    @FXML
    private TableColumn<Relic, String> colSiteLocate;

    @FXML
    private SearchBarController searchBarController;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colSiteId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSiteName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSiteDate.setCellValueFactory(new PropertyValueFactory<>("approvedYear"));
        colSiteLocate.setCellValueFactory(new PropertyValueFactory<>("location"));
        siteTable.setItems(RelicCollection.collection.getData());
        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        siteTable.setItems(RelicCollection.collection.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            siteTable.setItems(RelicCollection.collection.searchByID(intId));
                        } catch (Exception e) {
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        siteTable.setItems(RelicCollection.collection.getData());
                    }
                });
        siteTable.setRowFactory(tableView -> {
            TableRow<Relic> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Relic relic = row.getItem();
                    try {
                        FXMLLoader loader = App.setRoot("placeDetail");
                        PlaceDetailController controller = loader.getController();
                        controller.setRelic(relic);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
}

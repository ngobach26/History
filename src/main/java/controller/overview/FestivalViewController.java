package controller.overview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import collection.FestivalCollection;
import controller.SearchBarController;
import controller.SearchBoxListener;
import controller.detail.FestivalDetailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
import model.Festival;

public class FestivalViewController implements Initializable {
    @FXML
    private TableView<Festival> fesTable;

    @FXML
    private TableColumn<Festival, Integer> colFesId;
    @FXML
    private TableColumn<Festival, String> colFesName;
    @FXML
    private TableColumn<Festival, String> colFesDate;
    @FXML
    private TableColumn<Festival, String> colFesLocate;

    @FXML
    private SearchBarController searchBarController;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colFesId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colFesDate.setCellValueFactory(new PropertyValueFactory<>("startingDay"));
        colFesLocate.setCellValueFactory(new PropertyValueFactory<>("location"));
        fesTable.setItems(FestivalCollection.collection.getData());
        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        fesTable.setItems(FestivalCollection.collection.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            fesTable.setItems(FestivalCollection.collection.searchByID(intId));
                        } catch (Exception e) {
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        fesTable.setItems(FestivalCollection.collection.getData());
                    }
                });
        fesTable.setRowFactory(tableView -> {
            TableRow<Festival> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Festival festival = row.getItem();
                    try {
                        FXMLLoader loader = App.setRoot("festivalDetail");
                        FestivalDetailController controller = loader.getController();
                        controller.setFestival(festival);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

    }

}

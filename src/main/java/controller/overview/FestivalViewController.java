package controller.overview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import collection.FestivalData;
import controller.SearchBarController;
import controller.SearchBoxListener;
import javafx.fxml.FXML;
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
        setupTableColumns();
        populateData();
        setupSearchBar();
        setupDoubleClickHandler();
    }

    private void setupTableColumns() {
        colFesId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colFesDate.setCellValueFactory(new PropertyValueFactory<>("startingDay"));
        colFesLocate.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void populateData() {
        fesTable.setItems(FestivalData.data.getData());
    }

    private void setupSearchBar() {
        searchBarController.setSearchBoxListener(new FestivalSearchBoxListener());
    }

    private void setupDoubleClickHandler() {
        fesTable.setRowFactory(tableView -> {
            TableRow<Festival> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Festival festival = row.getItem();
                    try {
                        App.pageNavigationService.handleViewtoDetail(festival);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    private class FestivalSearchBoxListener implements SearchBoxListener {
        @Override
        public void handleSearchName(String name) {
            fesTable.setItems(FestivalData.data.searchByName(name));
        }

        @Override
        public void handleSearchId(String id) {
            try {
                int intId = Integer.parseInt(id);
                fesTable.setItems(FestivalData.data.searchByID(intId));
            } catch (Exception e) {
                System.err.println("Cannot find the entity with the id " + id);
            }
        }

        @Override
        public void handleBlank() {
            fesTable.setItems(FestivalData.data.getData());
        }
    }
}

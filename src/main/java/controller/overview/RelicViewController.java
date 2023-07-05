package controller.overview;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import collection.FigureData;
import collection.RelicData;
import controller.SearchBarController;
import controller.SearchBoxListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
import model.Figure;
import model.Relic;

public class RelicViewController implements Initializable {
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
        setupTableColumns();
        populateData();
        setupSearchBar();
        setupDoubleClickHandler();
    }

    private void setupTableColumns() {
        colSiteId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSiteName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSiteDate.setCellValueFactory(new PropertyValueFactory<>("approvedYear"));
        colSiteLocate.setCellValueFactory(new PropertyValueFactory<>("location"));
    }
    private void populateData() {
        siteTable.setItems(RelicData.data.getData());
    }

    private void setupSearchBar() {
        searchBarController.setSearchBoxListener(new RelicSearchBoxListener());
    }

    private void setupDoubleClickHandler() {
        siteTable.setRowFactory(tableView -> {
            TableRow<Relic> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Relic relic = row.getItem();
                    try {
                        App.pageNavigationService.handleViewtoDetail(relic);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    private class RelicSearchBoxListener implements SearchBoxListener {
        @Override
        public void handleSearchName(String name) {
            siteTable.setItems(RelicData.data.searchByName(name));
        }

        @Override
        public void handleSearchId(String id) {
            try {
                int intId = Integer.parseInt(id);
                siteTable.setItems(RelicData.data.searchByID(intId));
            } catch (Exception e) {
                System.err.println("Cannot find the entity with the id " + id);
            }
        }

        @Override
        public void handleBlank() {
            siteTable.setItems(RelicData.data.getData());
        }
    }
}

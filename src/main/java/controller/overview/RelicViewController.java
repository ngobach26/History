package controller.overview;

import java.net.URL;
import java.util.ResourceBundle;

import collection.RelicData;
import controller.SearchBarController;
import controller.SearchBoxListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
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
        setupClickHandlerForEachRow();
    }

    private void setupTableColumns() {
        colSiteId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSiteName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSiteDate.setCellValueFactory(new PropertyValueFactory<>("approvedYear"));
        colSiteLocate.setCellValueFactory(new PropertyValueFactory<>("location"));
    }
    private void populateData() {
        siteTable.setItems(RelicData.getRelicCollection().getData());
    }

    private void setupSearchBar() {
        searchBarController.setSearchBoxListener(new RelicSearchBoxListener());
    }

    private void setupClickHandlerForEachRow() {
        siteTable.setRowFactory(tableView -> {
            TableRow<Relic> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Relic relic = row.getItem();
                    App.pageNavigationService.handleViewtoDetail(relic);
                }
            });
            return row;
        });
    }

    private class RelicSearchBoxListener implements SearchBoxListener {
        @Override
        public void handleSearchName(String name) {
            siteTable.setItems(RelicData.getRelicCollection().searchByName(name));
        }

        @Override
        public void handleSearchId(String id) {
            try {
                int intId = Integer.parseInt(id);
                siteTable.setItems(RelicData.getRelicCollection().searchByID(intId));
            } catch (NumberFormatException e) {
                System.err.println("Wrong number format or cannot find the entity with the id " + id);
            }
        }

        @Override
        public void handleBlank() {
            siteTable.setItems(RelicData.getRelicCollection().getData());
        }
    }
}

package controller.overview;

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
        setupClickHandlerForEachRow();
    }

    private void setupTableColumns() {
        colFesId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colFesDate.setCellValueFactory(new PropertyValueFactory<>("startingDay"));
        colFesLocate.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void populateData() {
        fesTable.setItems(FestivalData.getFestivalCollection().getData());
    }

    private void setupSearchBar() {
        searchBarController.setSearchBoxListener(new FestivalSearchBoxListener());
    }

    private void setupClickHandlerForEachRow() {
        fesTable.setRowFactory(tableView -> {
            TableRow<Festival> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Festival festival = row.getItem();
                    App.searchHistoryService.addToSearchHistory(festival);
                    App.pageNavigationService.handleViewtoDetail(festival);
                }
            });
            return row;
        });
    }

    private class FestivalSearchBoxListener implements SearchBoxListener {
        @Override
        public void handleSearchName(String name) {
            fesTable.setItems(FestivalData.getFestivalCollection().searchByName(name));
        }

        @Override
        public void handleSearchId(String id) {
            try {
                int intId = Integer.parseInt(id);
                fesTable.setItems(FestivalData.getFestivalCollection().searchByID(intId));
            } catch (NumberFormatException e) {
                System.err.println("Wrong number format or cannot find the entity with the id " + id);
            }
        }

        @Override
        public void handleBlank() {
            fesTable.setItems(FestivalData.getFestivalCollection().getData());
        }
    }
}

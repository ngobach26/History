package controller.overview;

import java.net.URL;
import java.util.ResourceBundle;

import collection.EraData;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
import model.Era;

public class EraViewController implements Initializable {

    @FXML
    private TableView<Era> eraTable;

    @FXML
    private TableColumn<Era, Integer> colEraId;
    @FXML
    private TableColumn<Era, String> colEraName;
    @FXML
    private TableColumn<Era, String> colEraDate;

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
        colEraId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEraName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEraDate.setCellValueFactory(cellData -> {
            String startYear = cellData.getValue().getStartYear();
            String endYear = cellData.getValue().getEndYear();
            return new SimpleStringProperty(startYear + " - " + endYear);
        });
    }

    private void populateData() {
        eraTable.setItems(EraData.getEraCollection().getData());
    }

    private void setupSearchBar() {
        searchBarController.setSearchBoxListener(new EraSearchBoxListener());
    }

    private void setupClickHandlerForEachRow() {
        eraTable.setRowFactory(tableView -> {
            TableRow<Era> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Era era = row.getItem();
                    App.pageNavigationService.handleViewtoDetail(era);
                }
            });
            return row;
        });
    }

    private class EraSearchBoxListener implements SearchBoxListener {
        @Override
        public void handleSearchName(String name) {
            eraTable.setItems(EraData.getEraCollection().searchByName(name));
        }

        @Override
        public void handleSearchId(String id) {
            try {
                int intId = Integer.parseInt(id);
                eraTable.setItems(EraData.getEraCollection().searchByID(intId));
            } catch (NumberFormatException e) {
                System.err.println("Wrong number format or cannot find the entity with the id " + id);
            }
        }

        @Override
        public void handleBlank() {
            eraTable.setItems(EraData.getEraCollection().getData());
        }
    }
}

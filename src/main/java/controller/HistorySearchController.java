package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
import services.HistorySearchService.History;

import java.net.URL;
import java.util.ResourceBundle;

public class HistorySearchController implements Initializable {

    @FXML
    private TableView<History> historyTableView;
    @FXML
    private TableColumn<History, String> colName;
    @FXML
    private TableColumn<History, String> colType;
    @FXML
    private TableColumn<History, String> colTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        populateData();
        setupClickHandlerForEachRow();
    }

    private void setupTableColumns() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeOfEntity"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }
    private void populateData() {
        historyTableView.setItems(App.searchHistoryService.getHistories());
    }

    private void setupClickHandlerForEachRow() {
        historyTableView.setRowFactory(tableView -> {
            TableRow<History> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    History history = row.getItem();
                    App.pageNavigationService.handleDetailToDetail(history.getId(),history.getTypeOfEntity());
                }
            });
            return row;
        });
    }


}

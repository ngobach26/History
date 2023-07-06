package controller.overview;

import java.net.URL;
import java.util.ResourceBundle;

import collection.EventData;
import controller.SearchBarController;
import controller.SearchBoxListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
import model.Event;

public class EventViewController implements Initializable {

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, Integer> colEventId;
    @FXML
    private TableColumn<Event, String> colEventName;
    @FXML
    private TableColumn<Event, String> colEventDate;
    @FXML
    private TableColumn<Event, String> colEventLocate;

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
        colEventId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEventDate.setCellValueFactory(cellData -> {
            String startYear = cellData.getValue().getStartYear();
            String endYear = cellData.getValue().getEndYear();
            String dateRange = endYear + "-" + startYear;
            return new SimpleStringProperty(dateRange);
        });
        colEventLocate.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void populateData() {
        eventTable.setItems(EventData.getEventCollection().getData());
    }

    private void setupSearchBar() {
        searchBarController.setSearchBoxListener(new EventSearchBoxListener());
    }

    private void setupDoubleClickHandler() {
        eventTable.setRowFactory(tableView -> {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Event ev = row.getItem();
                    App.clickBackService.handleViewtoDetail(ev);
                }
            });
            return row;
        });
    }

    private class EventSearchBoxListener implements SearchBoxListener {
        @Override
        public void handleSearchName(String name) {
            eventTable.setItems(EventData.getEventCollection().searchByName(name));
        }

        @Override
        public void handleSearchId(String id) {
            try {
                int intId = Integer.parseInt(id);
                eventTable.setItems(EventData.getEventCollection().searchByID(intId));
            } catch (NumberFormatException e) {
                System.err.println("Wrong number format or cannot find the entity with the id " + id);
            }
        }

        @Override
        public void handleBlank() {
            eventTable.setItems(EventData.getEventCollection().getData());
        }
    }
}

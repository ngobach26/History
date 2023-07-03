package controller.overview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import collection.EventData;
import controller.SearchBarController;
import controller.SearchBoxListener;
import controller.helper.HandleDetailHelp;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.*;
import javafx.scene.control.*;
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
        colEventId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEventDate.setCellValueFactory(cellData -> {
            String startYear = cellData.getValue().getStartYear();
            String endYear = cellData.getValue().getEndYear();
            String dateRange = endYear + "-" + startYear;
            return new SimpleStringProperty(dateRange);
        });
        colEventLocate.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventTable.setItems(EventData.data.getData());

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        eventTable.setItems(EventData.data.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            eventTable.setItems(EventData.data.searchByID(intId));
                        } catch (Exception e) {
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        eventTable.setItems(EventData.data.getData());
                    }
                });

        eventTable.setRowFactory(tableView -> {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Event ev = row.getItem();
                    try {
                        HandleDetailHelp.Event(ev);
                        App.clickBackService.addEntityToClickBackStack(ev);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

    }
}

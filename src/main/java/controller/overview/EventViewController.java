package controller.overview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import collection.EventCollection;
import controller.SearchBarController;
import controller.SearchBoxListener;
import controller.detail.EventDetailController;
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
        colEventDate.setCellValueFactory(new PropertyValueFactory<>("time"));
        colEventLocate.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventTable.setItems(EventCollection.collection.getData());

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        eventTable.setItems(EventCollection.collection.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            eventTable.setItems(EventCollection.collection.searchByID(intId));
                        } catch (Exception e) {
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        eventTable.setItems(EventCollection.collection.getData());
                    }
                });

        eventTable.setRowFactory(tableView -> {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Event ev = row.getItem();
                    try {
                        FXMLLoader loader = App.setRoot("eventDetail");
                        EventDetailController controller = loader.getController();
                        controller.setEvent(ev);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

    }
}

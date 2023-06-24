package controller.overview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import collection.EraCollection;
import controller.SearchBarController;
import controller.SearchBoxListener;
import controller.detail.EraDetailController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private TableColumn<Era, String> colEraTimeStamp;
    @FXML
    private SearchBarController searchBarController;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colEraId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEraName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEraDate.setCellValueFactory(cellData -> {
            String startYear = cellData.getValue().getStartYear();
            String endYear =cellData.getValue().getEndYear();
            return new SimpleStringProperty(startYear + " - " + endYear);
        });
        eraTable.setItems(EraCollection.getCollection().getData());
        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        eraTable.setItems(EraCollection.getCollection().searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            eraTable.setItems(EraCollection.getCollection().searchByID(intId));
                        } catch (Exception e){
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        eraTable.setItems(EraCollection.getCollection().getData());
                    }
                }
        );
        eraTable.setRowFactory(tableView -> {
            TableRow<Era> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Era era = row.getItem();
                    try {
                        FXMLLoader loader =  App.setRoot("EraDetail");
                        EraDetailController controller = loader.getController();
                        controller.setEra(era);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        
    }
}

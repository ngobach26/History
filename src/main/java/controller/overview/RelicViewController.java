package controller.overview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import collection.RelicData;
import controller.SearchBarController;
import controller.SearchBoxListener;
import helper.HandleDetailHelp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
import model.Relic;

public class RelicViewController implements Initializable{
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
        colSiteId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSiteName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSiteDate.setCellValueFactory(new PropertyValueFactory<>("approvedYear"));
        colSiteLocate.setCellValueFactory(new PropertyValueFactory<>("location"));
        siteTable.setItems(RelicData.data.getData());
        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
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
                });
        siteTable.setRowFactory(tableView -> {
            TableRow<Relic> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Relic relic = row.getItem();
                    try {
                        HandleDetailHelp.Relic(relic);
                        App.clickBackService.addEntityToClickBackStack(relic);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
}

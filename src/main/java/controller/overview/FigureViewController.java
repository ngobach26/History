package controller.overview;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import collection.FigureData;
import controller.SearchBarController;
import controller.SearchBoxListener;
import controller.helper.HandleDetailHelp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
import model.Figure;

public class FigureViewController implements Initializable {
    @FXML
    private TableView<Figure> tblFigure;
    @FXML
    private TableColumn<Figure, Integer> colFigureId;
    @FXML
    private TableColumn<Figure, List<String>> colFigureName;
    @FXML
    private TableColumn<Figure, Map<String, Integer>> colFigureEra;
    @FXML
    private TableColumn<Figure, String> colFigureOverview;
    @FXML
    private SearchBarController searchBarController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colFigureId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFigureName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        colFigureEra.setCellFactory(column -> {
            TableCell<Figure, Map<String, Integer>> cell = new TableCell<Figure, Map<String, Integer>>() {
                @Override
                protected void updateItem(Map<String, Integer> eras, boolean empty) {
                    super.updateItem(eras, empty);
        
                    if (eras == null || empty) {
                        setText(null);
                    } else {
                        String erasString = eras.keySet().stream().collect(Collectors.joining(", "));
                        setText(erasString);
                    }
                }
            };
            return cell;
        });
        colFigureEra.setCellValueFactory(new PropertyValueFactory<>("eras"));
        colFigureOverview.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblFigure.setItems(FigureData.data.getData());
        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        tblFigure.setItems(FigureData.data.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            tblFigure.setItems(FigureData.data.searchByID(intId));
                        } catch (Exception e){
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        tblFigure.setItems(FigureData.data.getData());
                    }
                }
        );
        tblFigure.setRowFactory(tableView -> {
            TableRow<Figure> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Figure figure = row.getItem();
                    try {
                        HandleDetailHelp.Figure(figure);
                        App.clickBackService.addEntityToClickBackStack(figure);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        // UIUtils.setFigureClickHandler(tblFigure);
        

    }

}

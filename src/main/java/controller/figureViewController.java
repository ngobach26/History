package controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import collection.FigureCollection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;
import model.Figure;

public class figureViewController implements Initializable {
    @FXML
    private TableView<Figure> tblFigure;
    @FXML
    private TableColumn<Figure, Integer> colFigureId;
    @FXML
    private TableColumn<Figure, ArrayList<String>> colFigureName;
    @FXML
    private TableColumn<Figure, HashMap<String, Integer>> colFigureEra;
    @FXML
    private TableColumn<Figure, String> colFigureOverview;
    @FXML
    private searchBarController searchBarController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colFigureId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFigureName.setCellFactory(column -> {
            TableCell<Figure, ArrayList<String>> cell = new TableCell<Figure, ArrayList<String>>() {
                @Override
                protected void updateItem(ArrayList<String> names, boolean empty) {
                    super.updateItem(names, empty);

                    if (names == null || empty) {
                        setText(null);
                    } else {
                        String namesString = String.join(", ", names);
                        setText(namesString);
                    }
                }
            };
            return cell;
        });
        colFigureName.setCellValueFactory(new PropertyValueFactory<>("names"));
        
        colFigureEra.setCellFactory(column -> {
            TableCell<Figure, HashMap<String, Integer>> cell = new TableCell<Figure, HashMap<String, Integer>>() {
                @Override
                protected void updateItem(HashMap<String, Integer> eras, boolean empty) {
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
        tblFigure.setItems(FigureCollection.collection.getData());
        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void handleSearchName(String name) {
                        tblFigure.setItems(FigureCollection.collection.searchByName(name));
                    }

                    @Override
                    public void handleSearchId(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            tblFigure.setItems(FigureCollection.collection.searchByID(intId));
                        } catch (Exception e){
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void handleBlank() {
                        tblFigure.setItems(FigureCollection.collection.getData());
                    }
                }
        );

        tblFigure.setRowFactory(tableView -> {
            TableRow<Figure> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Figure figure = row.getItem();
                    try {
                        FXMLLoader loader =  App.setRoot("figureDetail");
                        figureDetailController controller = loader.getController();
                        controller.setFigure(figure);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

    }

}

package controller.overview;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import collection.FigureData;
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
        setupTableColumns();
        populateData();
        setupSearchBar();
        setupClickHandlerForEachRow();
    }

    private void setupTableColumns() {
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
    }

    private void populateData() {
        tblFigure.setItems(FigureData.getFigureCollection().getData());
    }

    private void setupSearchBar() {
        searchBarController.setSearchBoxListener(new FigureSearchBoxListener());
    }

    private void setupClickHandlerForEachRow() {
        tblFigure.setRowFactory(tableView -> {
            TableRow<Figure> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Figure figure = row.getItem();
                    App.pageNavigationService.handleViewtoDetail(figure);
                }
            });
            return row;
        });
    }

    private class FigureSearchBoxListener implements SearchBoxListener {
        @Override
        public void handleSearchName(String name) {
            tblFigure.setItems(FigureData.getFigureCollection().searchByName(name));
        }

        @Override
        public void handleSearchId(String id) {
            try {
                int intId = Integer.parseInt(id);
                tblFigure.setItems(FigureData.getFigureCollection().searchByID(intId));
            } catch (NumberFormatException e){
                System.err.println("Wrong number format or cannot find the entity with the id " + id);
            }
        }

        @Override
        public void handleBlank() {
            tblFigure.setItems(FigureData.getFigureCollection().getData());
        }
    }

}

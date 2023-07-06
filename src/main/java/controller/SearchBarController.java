package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controller for the search bar component.
 */
public class SearchBarController {
    @FXML
    public ComboBox<String> filterComboBox;
    @FXML
    private TextField searchBox;
    private SearchBoxListener searchBoxListener;

    public void setSearchBoxListener(SearchBoxListener searchBoxListener) {
        this.searchBoxListener = searchBoxListener;
    }

    @FXML
    public void initialize() {
        setupFilterComboBox();
        setupSearchBox();
    }

    private void setupFilterComboBox() {
        filterComboBox.setItems(FXCollections.observableArrayList("By Name", "By ID"));
        filterComboBox.getSelectionModel().selectFirst();
    }

    private void setupSearchBox() {
        searchBox.textProperty().addListener((observableValue, oldValue, newValue) -> handleSearchBoxChange(newValue));
    }

    /**
     * Xử lý sự kiện khi giá trị tìm kiếm trong search box thay đổi.
     *
     * @param newValue giá trị mới của search box
     */
    private void handleSearchBoxChange(String newValue) {
        String searchValue = newValue.trim();
        if (searchValue.isEmpty()) {
            searchBoxListener.handleBlank();
        } else {
            String selectedItem = filterComboBox.getValue();
            if ("By ID".equals(selectedItem)) {
                searchBoxListener.handleSearchId(searchValue);
            } else if ("By Name".equals(selectedItem)) {
                searchBoxListener.handleSearchName(searchValue);
            }
        }
    }
}

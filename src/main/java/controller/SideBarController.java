package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.App;
import main.EntityPages;
import main.StaticPages;

import java.io.IOException;

public class SideBarController {

    @FXML
    void mainSwitch(ActionEvent event) throws IOException {
        App.setAndReturnRoot(StaticPages.HOME_PAGE.getUrl());
    }

    @FXML
    void figuresSwitch(ActionEvent event) throws IOException {
        App.setAndReturnRoot(EntityPages.FIGURE_PAGES.getViewPage());
        App.pageNavigationService.handleClickBacktoViews();
    }

    @FXML
    void relicSwitch(ActionEvent event) throws IOException {
        App.setAndReturnRoot(EntityPages.RELIC_PAGES.getViewPage());
        App.pageNavigationService.handleClickBacktoViews();
    }

    @FXML
    void erasSwitch(ActionEvent event) throws IOException {
        App.setAndReturnRoot(EntityPages.ERA_PAGES.getViewPage());
        App.pageNavigationService.handleClickBacktoViews();
    }

    @FXML
    void eventsSwitch(ActionEvent event) throws IOException {
        App.setAndReturnRoot(EntityPages.EVENT_PAGES.getViewPage());
        App.pageNavigationService.handleClickBacktoViews();
    }

    @FXML
    void festivalsSwitch(ActionEvent event) throws IOException {
        App.setAndReturnRoot(EntityPages.FESTIVAL_PAGES.getViewPage());
        App.pageNavigationService.handleClickBacktoViews();
    }

    @FXML
    void aboutusSwitch(ActionEvent event) throws IOException {
        App.setAndReturnRoot(StaticPages.ABOUT_US_PAGE.getUrl());
        App.pageNavigationService.handleClickBacktoViews();
    }

    public void searchSwitch(ActionEvent actionEvent) {
    }

    public void exitSwitch(ActionEvent event) throws IOException {
        Platform.exit();
    }
}

package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.App;
import main.EntityPages;
import main.OtherPages;

public class SideBarController {

    @FXML
    void mainSwitch(ActionEvent event){
        App.pageNavigationService.handleSidebarAction(OtherPages.HOME_PAGE.getUrl());
    }

    @FXML
    void figuresSwitch(ActionEvent event){
        App.pageNavigationService.handleSidebarAction(EntityPages.FIGURE_PAGES.getViewPage());
    }

    @FXML
    void relicSwitch(ActionEvent event){
        App.pageNavigationService.handleSidebarAction(EntityPages.RELIC_PAGES.getViewPage());
    }

    @FXML
    void erasSwitch(ActionEvent event){
        App.pageNavigationService.handleSidebarAction(EntityPages.ERA_PAGES.getViewPage());
    }

    @FXML
    void eventsSwitch(ActionEvent event){
        App.pageNavigationService.handleSidebarAction(EntityPages.EVENT_PAGES.getViewPage());
    }

    @FXML
    void festivalsSwitch(ActionEvent event){
        App.pageNavigationService.handleSidebarAction(EntityPages.FESTIVAL_PAGES.getViewPage());
    }

    @FXML
    void aboutusSwitch(ActionEvent event){
        App.pageNavigationService.handleSidebarAction(OtherPages.ABOUT_US_PAGE.getUrl());
    }
    @FXML
    public void searchHistorySwitch(ActionEvent actionEvent) {
        App.pageNavigationService.handleSidebarAction(OtherPages.SEARCH_HISTORY_PAGE.getUrl());
    }

    public void exitSwitch(ActionEvent event){
        Platform.exit();
    }
}

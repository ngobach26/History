package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.App;
import main.EntityPages;
import main.StaticPages;

public class SideBarController {

    @FXML
    void mainSwitch(ActionEvent event){
        App.setRoot(StaticPages.HOME_PAGE.getUrl());
    }

    @FXML
    void figuresSwitch(ActionEvent event){
        App.setRoot(EntityPages.FIGURE_PAGES.getViewPage());
        App.clickBackService.handleClickBacktoViews();
    }

    @FXML
    void relicSwitch(ActionEvent event){
        App.setRoot(EntityPages.RELIC_PAGES.getViewPage());
        App.clickBackService.handleClickBacktoViews();
    }

    @FXML
    void erasSwitch(ActionEvent event){
        App.setRoot(EntityPages.ERA_PAGES.getViewPage());
        App.clickBackService.handleClickBacktoViews();
    }

    @FXML
    void eventsSwitch(ActionEvent event){
        App.setRoot(EntityPages.EVENT_PAGES.getViewPage());
        App.clickBackService.handleClickBacktoViews();
    }

    @FXML
    void festivalsSwitch(ActionEvent event){
        App.setRoot(EntityPages.FESTIVAL_PAGES.getViewPage());
        App.clickBackService.handleClickBacktoViews();
    }

    @FXML
    void aboutusSwitch(ActionEvent event){
        App.setRoot(StaticPages.ABOUT_US_PAGE.getUrl());
        App.clickBackService.handleClickBacktoViews();
    }

    public void searchSwitch(ActionEvent actionEvent) {
    }

    public void exitSwitch(ActionEvent event){
        Platform.exit();
    }
}

package services.PageNavigationService;

import collection.*;
import main.App;
import model.*;

import java.util.*;

/**
 * Class này dùng để chuyển hướng các trang và handle để có thể chuyển hướng về trang trước đó.
 */
public class PageNavigationService {
    public Stack<HistoricalEntity> clickBackStack = new Stack<HistoricalEntity>();
    private HistoricalEntity currentView;

    public void handleSidebarAction(String page) {
        if (!clickBackStack.isEmpty()) {
            clickBackStack.clear();
        }
        App.setRoot(page);
        currentView = null;
    }
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        Stack<HistoricalEntity> tempStack = new Stack<>();
//        int count = 0;
//        while (count < 5 && !clickBackStack.isEmpty()) {
//            HistoricalEntity entity = clickBackStack.pop();
//            sb.append(entity.getName()).append("\n");
//            tempStack.push(entity);
//            count++;
//        }
//        while (!tempStack.isEmpty()) {
//            clickBackStack.push(tempStack.pop());
//        }
//        return sb.append("----------").toString();
//    }


    public void handleViewtoDetail(HistoricalEntity entity) {
        App.searchHistoryService.addToSearchHistory(entity);
        currentView = entity;
        currentView.navigatePage();
    }

    /**
     * Xử lý sự kiện quay lại trang trước đó.
     *
     * @param defaultPage Trang mặc định khi không có trang trước đó trong ngăn xếp các trang đã truy cập.
     */
    public void handleBackToPreviousPage(String defaultPage) {
        if(clickBackStack.isEmpty()){
            App.setRoot(defaultPage);
            return;
        }
        HistoricalEntity entity = clickBackStack.pop();
        System.out.println(App.pageNavigationService.toString());
        entity.navigatePage();
    }

    public void handleDetailToDetail(int id,String type){
        try {
            addEntityToClickBackStack(currentView);
            System.out.println(App.pageNavigationService.toString());
            setDataToCurrentView(id, type);
            currentView.navigatePage();
            App.searchHistoryService.addToSearchHistory(currentView);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void setDataToCurrentView(int id, String type) {
        switch (type) {
            case "Era":
                Era era = EraData.getEraCollection().getById(id);
                currentView = era;
                break;
            case "Festival":
                Festival festival = FestivalData.getFestivalCollection().getById(id);
                currentView = festival;
                break;
            case "Figure":
                Figure figure = FigureData.getFigureCollection().getById(id);
                currentView = figure;
                break;
            case "Event":
                Event event = EventData.getEventCollection().getById(id);
                currentView = event;
                break;
            case "Relic":
                Relic relic = RelicData.getRelicCollection().getById(id);
                currentView = relic;
                break;
            default:
                throw new IllegalArgumentException("Invalid entity type: " + type);
        }
    }

    private void addEntityToClickBackStack(HistoricalEntity entity) {
        try{
            if(entity!=null){
                clickBackStack.push(entity);
            }
        }catch (StackOverflowError e){
            System.err.println("The click back stack is overflow! Don't navigate link anymore");
            clickBackStack.clear();
        }

    }
}


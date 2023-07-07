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
        if (entity instanceof Figure) {
            HandleDetailHelp.Figure(entity);
        } else if (entity instanceof Era) {
            HandleDetailHelp.Era(entity);
        } else if (entity instanceof Event) {
            HandleDetailHelp.Event(entity);
        } else if (entity instanceof Festival) {
            HandleDetailHelp.Festival(entity);
        } else if (entity instanceof Relic) {
            HandleDetailHelp.Relic(entity);
        }
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
        if (entity instanceof Figure) {
            HandleDetailHelp.Figure(entity);
        } else if (entity instanceof Era) {
            HandleDetailHelp.Era(entity);
        } else if (entity instanceof Event) {
            HandleDetailHelp.Event(entity);
        } else if (entity instanceof Festival) {
            HandleDetailHelp.Festival(entity);
        } else if (entity instanceof Relic) {
            HandleDetailHelp.Relic(entity);
        }
    }

    public void handleDetailToDetail(int id,String type){
        try {
            addEntityToClickBackStack(currentView);
            System.out.println(App.pageNavigationService.toString());
            switch (type) {
                case "Era":
                    Era era = EraData.getEraCollection().getById(id);
                    currentView = era;
                    HandleDetailHelp.Era(era);
                    App.searchHistoryService.addToSearchHistory(era);
                    break;
                case "Festival":
                    Festival festival = FestivalData.getFestivalCollection().getById(id);
                    currentView = festival;
                    HandleDetailHelp.Festival(festival);
                    App.searchHistoryService.addToSearchHistory(festival);
                    break;
                case "Figure":
                    Figure figure = FigureData.getFigureCollection().getById(id);
                    currentView = figure;
                    HandleDetailHelp.Figure(figure);
                    App.searchHistoryService.addToSearchHistory(figure);
                    break;
                case "Event":
                    Event event = EventData.getEventCollection().getById(id);
                    currentView = event;
                    HandleDetailHelp.Event(event);
                    App.searchHistoryService.addToSearchHistory(event);
                    break;
                case "Relic":
                    Relic relic = RelicData.getRelicCollection().getById(id);
                    HandleDetailHelp.Relic(relic);
                    App.searchHistoryService.addToSearchHistory(relic);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid entity type: " + type);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
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


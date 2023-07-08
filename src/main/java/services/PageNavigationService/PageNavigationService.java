package services.PageNavigationService;

import collection.*;
import controller.detail.*;
import main.App;
import main.EntityPages;
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


    public void handleViewtoDetail(HistoricalEntity entity) {
        App.searchHistoryService.addToSearchHistory(entity);
        currentView = entity;
        if (entity instanceof Figure) {
            HandleDetailHelp.<Figure,FigureDetailController>handeDetail(entity,EntityPages.FIGURE_PAGES.getDetailPage());
        } else if (entity instanceof Era) {
            HandleDetailHelp.<Era, EraDetailController>handeDetail(entity,EntityPages.ERA_PAGES.getDetailPage());
        } else if (entity instanceof Event) {
            HandleDetailHelp.<Event,EventDetailController>handeDetail(entity,EntityPages.EVENT_PAGES.getDetailPage());
        } else if (entity instanceof Festival) {
            HandleDetailHelp.<Festival, FestivalDetailController>handeDetail(entity,EntityPages.FESTIVAL_PAGES.getDetailPage());
        } else if (entity instanceof Relic) {
            HandleDetailHelp.<Relic, RelicDetailController>handeDetail(entity,EntityPages.RELIC_PAGES.getDetailPage());
        }
    }

    /**
     * Xử lý sự kiện quay lại trang detail trước đó.
     *
     * @param defaultPage Trang mặc định khi không có trang trước đó trong ngăn xếp các trang đã truy cập.
     */
    public void handleBackToPreviousPage(String defaultPage) {
        if(clickBackStack.isEmpty()){
            App.setRoot(defaultPage);
            return;
        }
        HistoricalEntity entity = clickBackStack.pop();
        if (entity instanceof Figure) {
            HandleDetailHelp.<Figure,FigureDetailController>handeDetail(entity,EntityPages.FIGURE_PAGES.getDetailPage());
        } else if (entity instanceof Era) {
            HandleDetailHelp.<Era, EraDetailController>handeDetail(entity,EntityPages.ERA_PAGES.getDetailPage());
        } else if (entity instanceof Event) {
            HandleDetailHelp.<Event,EventDetailController>handeDetail(entity,EntityPages.EVENT_PAGES.getDetailPage());
        } else if (entity instanceof Festival) {
            HandleDetailHelp.<Festival, FestivalDetailController>handeDetail(entity,EntityPages.FESTIVAL_PAGES.getDetailPage());
        } else if (entity instanceof Relic) {
            HandleDetailHelp.<Relic, RelicDetailController>handeDetail(entity,EntityPages.RELIC_PAGES.getDetailPage());
        }
    }

    public void handleDetailToDetail(int id,String type){
        try {
            addEntityToClickBackStack(currentView);
            switch (type) {
                case "Era":
                    Era era = EraData.getEraCollection().getById(id);
                    currentView = era;
                    HandleDetailHelp.<Era, EraDetailController>handeDetail(era,EntityPages.ERA_PAGES.getDetailPage());
                    App.searchHistoryService.addToSearchHistory(era);
                    break;

                case "Festival":
                    Festival festival = FestivalData.getFestivalCollection().getById(id);
                    currentView = festival;
                    HandleDetailHelp.<Festival, FestivalDetailController>handeDetail(festival,EntityPages.FESTIVAL_PAGES.getDetailPage());
                    App.searchHistoryService.addToSearchHistory(festival);
                    break;

                case "Figure":
                    Figure figure = FigureData.getFigureCollection().getById(id);
                    currentView = figure;
                    HandleDetailHelp.<Figure, FigureDetailController>handeDetail(figure, EntityPages.FIGURE_PAGES.getDetailPage());
                    App.searchHistoryService.addToSearchHistory(figure);
                    break;
                case "Event":
                    Event event = EventData.getEventCollection().getById(id);
                    currentView = event;
                    HandleDetailHelp.<Event, EventDetailController>handeDetail(event, EntityPages.EVENT_PAGES.getDetailPage());
                    App.searchHistoryService.addToSearchHistory(event);
                    break;
                case "Relic":
                    Relic relic = RelicData.getRelicCollection().getById(id);
                    HandleDetailHelp.<Relic, RelicDetailController>handeDetail(relic,EntityPages.RELIC_PAGES.getDetailPage());
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


package services;

import collection.EraData;
import helper.HandleDetailHelp;
import model.*;

import java.io.IOException;
import java.util.*;

public class PageNavigationService {
    public Stack<HistoricalEntity> clickBackStack = new Stack<HistoricalEntity>();
    private HistoricalEntity currentView;

    public void handleClickBacktoViews(){
        if(!clickBackStack.isEmpty()){
            clickBackStack.clear();
        }
        currentView = null;
    }

    public void addEntityToClickBackStack(HistoricalEntity entity){
        clickBackStack.push(entity);
    }

    public void handleViewtoDetail(HistoricalEntity entity) throws IOException{
        currentView = entity;
        if (entity instanceof Figure) {
            HandleDetailHelp.Figure(entity);
        }
        else if (entity instanceof Era) {
            HandleDetailHelp.Era(entity);
        }
        else if (entity instanceof Event) {
            HandleDetailHelp.Event(entity);
        }
        else if (entity instanceof Festival) {
            HandleDetailHelp.Festival(entity);
        }
        else if (entity instanceof Relic) {
            HandleDetailHelp.Relic(entity);
        }
    }

    public void handleBackToPreDetailPage() throws IOException {
        HistoricalEntity entity = clickBackStack.pop();
        if (entity instanceof Figure) {
            HandleDetailHelp.Figure(entity);
        }
        else if (entity instanceof Era) {
            HandleDetailHelp.Era(entity);
        }
        else if (entity instanceof Event) {
            HandleDetailHelp.Event(entity);
        }
        else if (entity instanceof Festival) {
           HandleDetailHelp.Festival(entity);
        }
        else if (entity instanceof Relic) {
            HandleDetailHelp.Relic(entity);
        }
    }

    public <T extends HistoricalEntity,C> void handleDetailToDetail(int id,HistoricalEntity entity){
    }




}

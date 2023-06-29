package controller.services;

import controller.helper.HandleDetailHelp;
import model.*;

import java.io.IOException;
import java.util.*;

public class CLickBackService {
    public Stack<HistoricalEntity> clickBackStack = new Stack<HistoricalEntity>();

    public  void handleClickBacktoViews(){
        if(!clickBackStack.isEmpty()){
            clickBackStack.clear();
        }
    }

    public void addEntityToClickBackStack(HistoricalEntity entity){
        clickBackStack.push(entity);
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



}

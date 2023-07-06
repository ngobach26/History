package services;

import helper.HandleDetailHelp;
import model.*;

import java.util.*;

/**
 * Class này dùng để chuyển hướng các trang và handle để có thể chuyển hướng về trang trước đó.
 */
public class ClickBackService {
    public Stack<HistoricalEntity> clickBackStack = new Stack<HistoricalEntity>();
    private HistoricalEntity currentView;

    public void handleClickBacktoViews() {
        if (!clickBackStack.isEmpty()) {
            clickBackStack.clear();
        }
        currentView = null;
    }

    public void addEntityToClickBackStack(HistoricalEntity entity) {
        clickBackStack.push(entity);
    }

    public void handleViewtoDetail(HistoricalEntity entity) {
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
     * Handle việc chuyển về trang trước đó.
     * Bằng cách lấy ra HistoricalEntity trong clickBackStack.
     */
    public void handleBackToPreDetailPage() {
        HistoricalEntity entity = clickBackStack.pop();
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
}


package controller.detail;

import javafx.event.ActionEvent;
import model.HistoricalEntity;

public interface DetailAction {
    public void setEntity(HistoricalEntity entity);
    public void onClickBack(ActionEvent event);
}

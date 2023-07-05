package controller.detail;

import javafx.event.ActionEvent;
import model.Era;
import model.HistoricalEntity;

import java.io.IOException;

public interface DetailAction {
    public void onClickBack(ActionEvent event) throws IOException;
    public void setEntity(HistoricalEntity entity);
}

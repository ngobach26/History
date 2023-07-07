package services.HistorySearchService;

import com.google.gson.reflect.TypeToken;
import helper.JsonIO;
import javafx.collections.*;
import model.HistoricalEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HistorySearchService {
    private static final Type TOKEN = new TypeToken<ArrayList<History>>() {}.getType();
    private static final String SOURCE_PATH = "src/main/resources/json/SearchHistory.json";
    private ObservableList<History> histories = FXCollections.observableArrayList();

    public void loadJson(){
        ArrayList<History> data = (ArrayList<History>) new JsonIO<History>(TOKEN).loadJson(SOURCE_PATH);
        this.histories = FXCollections.observableArrayList(data);
    }

    public void writeJson(){
        ArrayList<History> data = new ArrayList<>(histories);
        new JsonIO<History>(TOKEN).writeJson(data, SOURCE_PATH);
    }

    public void addToSearchHistory(HistoricalEntity entity){
        histories.add(0, new History(entity.getId(), entity.getName(), entity.getClass().getSimpleName()));
    }

    public ObservableList<History> getHistories() {
        return histories;
    }

}

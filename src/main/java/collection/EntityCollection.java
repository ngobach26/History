package collection;

import java.util.ArrayList;

import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import model.HistoricalEntity;

public class EntityCollection<T extends HistoricalEntity> {
    private ObservableList<T> data = FXCollections.observableArrayList();

    public void setData(ArrayList<T> d) {
        this.data = FXCollections.observableArrayList(d);
    }

    public T get(Integer id){
        return data.get(id-1);
    }
    public T findName(String name){
        for (T entity : data){
            if (entity.getName().equals(name)) return entity;
        }
        return null;
    }

    public ObservableList<T> getData() {
        return this.data;
    }

    public FilteredList<T> searchByName(String name) {
        return new FilteredList<>(data, entity -> entity.containsName(name));
    }

    public FilteredList<T> searchByID(int id) {
        return new FilteredList<>(data, entity -> entity.containsID(id));
    }
}

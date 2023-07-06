package collection;

import java.util.ArrayList;

import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import model.HistoricalEntity;


/**
 * Collection class nhằm lưu trữ các thực thể đóng vai trò như một database.
 *
 * @param <T>
 */
public class EntityCollection<T extends HistoricalEntity> {
    private ObservableList<T> data = FXCollections.observableArrayList();

    public void setData(ArrayList<T> d) {
        this.data = FXCollections.observableArrayList(d);
    }

    public T getById(Integer id){
        for(T entity : data){
            if(entity.getId() == id) return entity;
        }
        return null;
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

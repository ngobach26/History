package model;

import java.util.ArrayList;

public class HistoricalEntity {
    protected int id;
    protected ArrayList<String> names;

    public HistoricalEntity(int id, ArrayList<String> names) {
        this.id = id;
        this.names = names;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        HistoricalEntity other = (HistoricalEntity) obj;

        if (id != other.id)
            return false;

        if (names == null && other.names == null)
            return true;

        if (names == null || other.names == null || names.size() != other.names.size())
            return false;

        for (int i = 0; i < names.size(); i++) {
            if (!names.get(i).equals(other.names.get(i)))
                return false;
        }

        return true;
    }

    public boolean containName(String name) {
        if (names == null || name == null)
            return false;
    
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).toLowerCase().contains(name.toLowerCase()))
                return true;
        }
    
        return false;
    }

    public boolean containID(int id) {
        return Integer.toString(this.getId()).contains(Integer.toString(id));
    }

    

}

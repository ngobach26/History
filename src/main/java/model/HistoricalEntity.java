package model;

public abstract class HistoricalEntity {
    protected int id;
    protected String name;
    protected String description;

    public HistoricalEntity(int id, String name,String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        if (name == null && other.name == null)
            return true;

        if (name == null || other.name == null || !name.equalsIgnoreCase(other.name))
            return false;

        return true;
    }

    public boolean containsName(String name) {
        if (name == null)
            return false;

        if (this.name == null)
            return false;

        return this.name.toLowerCase().contains(name.toLowerCase());
    }

    public boolean containsID(int id) {
        return this.id == id;
    }
}

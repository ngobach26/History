package model;

public abstract class HistoricalEntity {
    protected int id;
    protected String name;
    protected String description;

    public HistoricalEntity(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}

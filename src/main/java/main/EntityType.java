package main;

public enum EntityType {
    ERA("Era"),
    FESTIVAL("Festival"),
    FIGURE("Figure"),
    EVENT("Event"),
    RELIC("Relic");

    private final String name;

    EntityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


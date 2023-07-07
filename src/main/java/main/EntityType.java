package main;

import model.*;

public enum EntityType {
    ERA(Era.class.getSimpleName()),
    FESTIVAL(Festival.class.getSimpleName()),
    FIGURE(Figure.class.getSimpleName()),
    EVENT(Event.class.getSimpleName()),
    RELIC(Relic.class.getSimpleName());

    private final String name;

    EntityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

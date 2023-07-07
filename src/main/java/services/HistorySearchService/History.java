package services.HistorySearchService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class History {
    private int id;
    private String name;
    private String typeOfEntity;
    private String timestamp;

    public History(int id, String name, String typeOfEntity) {
        this.id = id;
        this.name = name;
        this.typeOfEntity = typeOfEntity;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.timestamp = now.format(formatter);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTypeOfEntity() {
        return typeOfEntity;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

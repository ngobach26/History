package model;

public class Relic extends HistoricalEntity{
    private static int numRelic = 0;
    private String location;
    private String category;
    private String approvedYear;

    public Relic(String name, String location, String category, String approvedYear, String description) {
        super(++numRelic, name, description);
        this.location = location;
        this.category = category;
        this.approvedYear = approvedYear;
    }
}

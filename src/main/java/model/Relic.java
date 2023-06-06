package model;


public class Relic extends HistoricalEntity{
    private static int numRelic = 0;
    private int id;
    private String name;
    private String location;
    private String category;
    private String approvedYear;
    private String overView;

    public Relic(String name, String location, String category, String approvedYear, String overView) {
        this.id = ++numRelic;
        this.name = name;
        this.location = location;
        this.category = category;
        this.approvedYear = approvedYear;
        this.overView = overView;
    }
}

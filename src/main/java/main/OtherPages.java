package main;

public enum OtherPages {
    ABOUT_US_PAGE("AboutUsView"),
    HOME_PAGE("HomepageView"),
    SEARCH_HISTORY_PAGE("HistorySearchView");

    private final String url;

    OtherPages(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

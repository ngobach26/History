package main;

public enum StaticPages {
    ABOUT_US_PAGE("AboutUsView"),
    HOME_PAGE("HomepageView");

    private String url;

    StaticPages(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

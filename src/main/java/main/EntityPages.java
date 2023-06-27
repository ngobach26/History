package main;

public enum EntityPages {
    FIGURE_PAGES("FigureView","FigureDetail"),
    ERA_PAGES("EraView","EraDetail"),
    EVENT_PAGES("EventView","EventDetail"),
    FESTIVAL_PAGES("FestivalView","FestivalDetail"),
    RELIC_PAGES("RelicView","RelicDetail");
    private String viewPage;
    private  String detailPage;

    EntityPages(String viewPage, String detailPage) {
        this.viewPage = viewPage;
        this.detailPage = detailPage;
    }

    public String getViewPage() {
        return viewPage;
    }

    public String getDetailPage() {
        return detailPage;
    }
}

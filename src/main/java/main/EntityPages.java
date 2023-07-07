package main;

public enum EntityPages {
    FIGURE_PAGES("overview/FigureView", "detail/FigureDetail"),
    ERA_PAGES("overview/EraView", "detail/EraDetail"),
    EVENT_PAGES("overview/EventView", "detail/EventDetail"),
    FESTIVAL_PAGES("overview/FestivalView", "detail/FestivalDetail"),
    RELIC_PAGES("overview/RelicView", "detail/RelicDetail");

    private final String viewPage;
    private final String detailPage;

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

package controller;

public interface SearchBoxListener {
    void handleSearchName(String name);

    void handleSearchId(String id);

    void handleBlank();
}

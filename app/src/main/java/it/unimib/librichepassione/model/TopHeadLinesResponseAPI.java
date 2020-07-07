package it.unimib.librichepassione.model;

import java.util.List;

public class TopHeadLinesResponseAPI {
    private int totalItems;
    private List<Book> items;

    public TopHeadLinesResponseAPI(int totalItems, List<Book> items) {
        this.totalItems = totalItems;
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "TopHeadLinesResponseAPI{" +
                "totalItems=" + totalItems +
                ", book=" + items +
                '}';
    }
}

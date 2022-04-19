package io.codelex.flightplanner.customerapi;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {
    private List<T> items = new ArrayList<>();
    private int page;
    private int totalItems;

    public PageResult(List<T> items, int page, int totalItems) {
        this.items = items;
        this.page = page;
        this.totalItems = totalItems;
    }

    public PageResult() {
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

}

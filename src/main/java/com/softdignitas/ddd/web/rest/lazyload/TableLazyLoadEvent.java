package com.softdignitas.ddd.web.rest.lazyload;

public class TableLazyLoadEvent {

    private Integer first;
    private Integer rows;
    private String sortField;
    private SortOrder sortOrder;
    private String filters;

    //    private String globalFilter;
    //    private SortMeta[] multiSortMeta;
    //    private Function<Object, Object> forceUpdate;
    //    private Integer last;

    // Getters and setters
    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }
}
